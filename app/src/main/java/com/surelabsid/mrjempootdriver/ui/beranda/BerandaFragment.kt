package com.surelabsid.mrjempootdriver.ui.beranda

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.databinding.FragmentBerandaBinding
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.viewmodel.HomeViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.ACCESS_LOCATION
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_DRIVER_PHOTO
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.value.ROUND_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestChangeStatusDriver
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.ewallet.PenarikanSaldoActivity
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.LoginViewModel
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseAppCost
import com.surelabsid.mrjempootdriver.ui.topup.TopUpActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*


@AndroidEntryPoint
class BerandaFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentBerandaBinding

    lateinit var viewModel: HomeViewModel

    lateinit var viewModelLogin: LoginViewModel

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var gpsTracker: GPSTracker

    @Inject
    lateinit var locationUpdater: LocationUpdater

    private var mMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModelLogin = ViewModelProvider(this).get(LoginViewModel::class.java)

        initStatus()


        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        initView()

        attachObserve(view)

    }

    private fun attachObserve(view: View) {
        with(viewModel) {
            home.observe(viewLifecycleOwner, { responseHome(it) })
            change_status_driver.observe(viewLifecycleOwner, { responseChangeStatusDriver(it) })
            app_cost.observe(viewLifecycleOwner, { responseAppCost(it) })
            app_settings.observe(viewLifecycleOwner, { responseAppSettings(it) })
            location_all_driver.observe(viewLifecycleOwner, { responseLocationAllDriver(it) })
            is_connected.observe(viewLifecycleOwner, { responseIsConnected(it, view) })
            error.observe(viewLifecycleOwner, { showError(view.context, it) })
        }

        viewModelLogin.login.observe(viewLifecycleOwner, { responseLogin(it) })
    }

    private fun responseLogin(responseLogin: ResponseLogin) {

        if (responseLogin.code == "200") {
            val item = responseLogin.data?.get(0)

            with(sessionManager) {
                id = item?.id.toString()
                full_name = item?.driverName.toString()
                ktp_id = item?.userNationid.toString()
                birth_date = item?.dob.toString()
                username = item?.phoneNumber.toString()
                dial_code = item?.countrycode.toString()
                email = item?.email.toString()
                photo_profile = item?.photo.toString()
                rating = item?.rating.toString()
                job = item?.job.toString()
                gender = item?.gender.toString()
                address = item?.driverAddress.toString()
                status = item?.status.toString()
                status_config_driver = item?.statusConfigDriver.toString()
                balance = item?.balance.toString()

            }

        } else {
            binding.root.showSnackbar("${responseLogin.message}", Snackbar.LENGTH_LONG)
        }

    }

    private fun responseAppCost(it: ResponseAppCost?) {
        if (it?.code == "200") {
            sessionManager.biaya_platform = it.data?.appCost ?: "0"
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseAppSettings(it: ResponseAppSettings?) {
        if (it?.code == 200) {
            sessionManager.app_settings = Json.encodeToString(it.appSettings)
            when(it.appSettings?.verihubsMode){
                "production" -> {
                    sessionManager.app_id_verihubs = it.appSettings.verihubsAppId.toString()
                    sessionManager.api_key_verihubs = it.appSettings.verihubsKey.toString()
                }
                else -> {
                    sessionManager.app_id_verihubs = it.appSettings?.verihubsAppIdSb.toString()
                    sessionManager.api_key_verihubs = it.appSettings?.verihubsKeySb.toString()
                }
            }

            val appSettings = Json.decodeFromString<AppSettings>(sessionManager.app_settings)

            Log.d("TAG", "provideOkhttpClientVerihubs: ${appSettings.verihubsAppId}")

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseChangeStatusDriver(it: ResponseChangeStatusDriver?) {
        if (it?.code == "200") {
            sessionManager.status_config_driver = it.data?.status.toString()
            binding.root.showSnackbar(it.message.toString(), Snackbar.LENGTH_SHORT)
        } else {
            initStatus()
            binding.root.showSnackbar(it?.message.toString(), Snackbar.LENGTH_SHORT)
        }

    }

    private fun responseIsConnected(it: Boolean?, view: View) {
        if (it == true) {
            //connect
        } else {
            Snackbar.make(view, R.string.no_internet_access, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry) {
                    val param = RequestHome(sessionManager.id, sessionManager.username)
                    with(viewModel) {
                        home(param)
                    }
                }
                .show()
        }
    }

    private fun responseHome(responseHome: ResponseHome) {

        binding.saldoDriver.text = toRupiah(responseHome.balance?.toDouble())

    }

    private fun initView() {

        val param_login = RequestLogin(sessionManager.username, sessionManager.password, sessionManager.token)
        viewModelLogin.login(param_login)

        val param = RequestHome(sessionManager.id, sessionManager.username)

        binding.textHalo.text = "Halo, ${sessionManager.full_name}"

        binding.buttonTarikSaldo2.setOnClickListener {
            activity?.openActivity(TopUpActivity::class.java)
//            activity?.openActivity(GokidzLiveStreamingActivityCopy::class.java)
        }

        binding.buttonTarikSaldo1.setOnClickListener {
            activity?.openActivity(PenarikanSaldoActivity::class.java)
        }

        greetings()

        initStatus()

        showProfile()

//        binding.switchMaterial.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
//            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
//                if (isChecked) {
//                    val param = RequestChangeStatusDriver(sessionManager.id, 1)
//                    viewModel.changeStatusDriver(param)
//                } else {
//                    val param = RequestChangeStatusDriver(sessionManager.id, 4)
//                    viewModel.changeStatusDriver(param)
//                }
//            }
//
//        })

        binding.switchMaterial.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.text = getString(R.string.aktif)
                val param = RequestChangeStatusDriver(sessionManager.id, 1)
                viewModel.changeStatusDriver(param)
            } else {
                buttonView.text = getString(R.string.tidak_aktif)
                val param = RequestChangeStatusDriver(sessionManager.id, 4)
                viewModel.changeStatusDriver(param)
            }
        }

        with(viewModel) {
            home(param)
            appCost()
            appSettings()
            locationAllDriver()
        }

    }


    private fun greetings() {
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.textGreeting.text = getString(R.string.selamat_pagi)
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            binding.textGreeting.text = getString(R.string.selamat_siang)
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            binding.textGreeting.text = getString(R.string.selamat_sore)
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            binding.textGreeting.text = getString(R.string.selamat_malam)
        }
    }

    private fun showProfile() {
        Glide.with(binding.circleImageView).load(BASE_URL_DRIVER_PHOTO+sessionManager.photo_profile)
            .transform(CenterCrop(), RoundedCorners(ROUND_IMAGE))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBarImage.hide()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBarImage.hide()
                    return false
                }

            })
            .error(R.drawable.bg_beranda).into(binding.circleImageView)
    }

//    private fun switchStatus() {
//        if (binding.switchMaterial.isActivated) {
//            binding.switchMaterial.text = "Aktif"
//        } else {
//            binding.switchMaterial.text = "Tidak Aktif"
//        }
//    }

    private fun initStatus() {
        if (sessionManager.status_config_driver == "1"){
            binding.switchMaterial.isChecked = true
            binding.switchMaterial.text = "Aktif"
        } else {
            binding.switchMaterial.isChecked = false
            binding.switchMaterial.text = "Tidak Aktif"
        }

    }

    private fun responseLocationAllDriver(responseLocationAllDriver: ResponseLocationAllDriver) {

        val data = responseLocationAllDriver.data
        val builder = LatLngBounds.builder()

//        Log.d("ARRAY LAT RIDER", latRider?.toString())

        for (i in data?.indices ?: 0..1) {
//            if (data?.get(i)?.vehicle == Constan.TypeVehicle.MOTORCYCLE) {
                mMap?.addMarker(
                    MarkerOptions().position(LatLng(data?.get(i)?.latitude?.toDouble() ?: 0.0, data?.get(i)?.longitude?.toDouble() ?: 0.0))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike_small))
                )
//            } else {
//                mMap?.addMarker(
//                    MarkerOptions().position(LatLng(latRider?.get(i) ?: 0.0, lngRider?.get(i) ?: 0.0))
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shop))
//                )
//            }

        }

        /*val width = resources.displayMetrics.widthPixels+10
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.20).toInt()
        val bound = builder.build()
        val cu = CameraUpdateFactory.newLatLngBounds(bound, width, height, padding)
        mMap?.animateCamera(cu)*/

//        showgps()
    }


    override fun onMapReady(p0: GoogleMap) {

        mMap = p0
        mMap?.isMyLocationEnabled = true
        mMap?.uiSettings?.isZoomControlsEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED == activity?.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                locationUpdater.start()
                showMarker()
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), ACCESS_LOCATION
                )
            }
        } else {
            locationUpdater.start()
        }

    }

    fun showMarker() {
        sessionManager.lat_user = gpsTracker.latitude.toString()
        sessionManager.lng_user = gpsTracker.longitude.toString()

        val latlng = LatLng(
            sessionManager.lat_user.toDouble(),
            sessionManager.lng_user.toDouble()
        )

        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,15f))
    }

    override fun onResume() {
        super.onResume()
        initView()
        binding.mapView.onResume()
        locationUpdater.resume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}