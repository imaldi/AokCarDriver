package com.surelabsid.mrjempootdriver.ui.profil

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.BuildConfig
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.DialogLogoutBinding
import com.surelabsid.mrjempootdriver.databinding.DialogProfileBinding
import com.surelabsid.mrjempootdriver.databinding.FragmentProfileBinding
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.PusatBantuanActivity
import com.surelabsid.mrjempootdriver.ui.jempootpoin.JempootPoinActivity
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogout
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.ProfileViewModel
import com.surelabsid.mrjempootdriver.ui.notifikasi.NotifikasiActivity
import com.surelabsid.mrjempootdriver.ui.pendaftaran.LoginDaftarActivity
import com.surelabsid.mrjempootdriver.ui.pendaftaran.SyaratKetentuanActivity
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseHealthDriver
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseLogout
import com.surelabsid.mrjempootdriver.ui.ulasan.UlasanActivity
import com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.VerifikasiKesahatanActivity
import com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.viewmodel.VerifikasiKesehatanViewModel
import com.surelabsid.mrjempootdriver.utils.*
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_DRIVER_PHOTO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var viewModel: ProfileViewModel
    lateinit var viewModelKesehatan: VerifikasiKesehatanViewModel

    lateinit var dialogLogout: Dialog

    lateinit var dialogLogoutBinding: DialogLogoutBinding

    lateinit var dialogVerifikasiKesehatan: Dialog

    lateinit var dialogProfileBinding: DialogProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModelKesehatan = ViewModelProvider(this).get(VerifikasiKesehatanViewModel::class.java)

        initView()

        attachObserve()

    }

    private fun attachObserve() {

        viewModelKesehatan.health_driver.observe(viewLifecycleOwner, { responseHealthDriver(it) })

        with(viewModel) {
            logout.observe(viewLifecycleOwner, { responseLogout(it) })
            error.observe(viewLifecycleOwner, { showError(context, it) })
            loading.observe(viewLifecycleOwner, { isLoading(it) })
        }
    }

    private fun responseHealthDriver(it: ResponseHealthDriver) {
        if (it.code == "200") {

            if (it.data?.dateHealty == null) {
                showDialogVerfikasiKesehatan()
            }

        } else {
            binding.root.showSnackbar(it.message.toString(), Snackbar.LENGTH_SHORT)
        }
    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            with(binding) {
                progressBar.visibility = View.VISIBLE
                buttonKeluar.visibility = View.GONE
            }
        } else {
            with(binding) {
                buttonKeluar.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showDialogLogout() {
        dialogLogoutBinding = DialogLogoutBinding.bind(View.inflate(context, R.layout.dialog_logout, null))
        dialogLogout = Dialog(binding.root.context).apply {
            setContentView(dialogLogoutBinding.root)
        }

        with(dialogLogoutBinding) {
            buttonYa.setOnClickListener {
                val param = RequestLogout(sessionManager.id)
                viewModel.logout(param)
            }
            buttonTidak.setOnClickListener {
                dialogLogout.dismiss()
            }
        }

        dialogLogout.show()
    }

    private fun showDialogVerfikasiKesehatan() {
        dialogProfileBinding = DialogProfileBinding.bind(View.inflate(context, R.layout.dialog_profile, null))
        dialogVerifikasiKesehatan = Dialog(binding.root.context).apply {
            setContentView(dialogProfileBinding.root)
        }

        with(dialogProfileBinding) {
            buttonOke.setOnClickListener {
                activity?.openActivity(VerifikasiKesahatanActivity::class.java)
                dialogVerifikasiKesehatan.dismiss()
            }

        }

        dialogVerifikasiKesehatan.show()
    }


    private fun responseLogout(it: ResponseLogout?) {
        if (it?.message == "success") {
            sessionManager.logout()

            val intent = Intent(context, LoginDaftarActivity::class.java)
            intent.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)

        } else {
            binding.root.showSnackbar(it?.message.toString(), Snackbar.LENGTH_LONG)
        }
    }

    private fun initView() {

        showProfile()

        viewModelKesehatan.healthDriver(sessionManager.id)

        with(binding) {
            textViewNamaUser.text = sessionManager.full_name
            textViewEmail.text = sessionManager.email
            textViewId.text = "ID : ${sessionManager.id}"

            textViewEditProfil.setOnClickListener {
                activity?.openActivity(EditProfileActivity::class.java)
            }

            textViewPrivacyPolicy.setOnClickListener {
                activity?.openActivity(KebijakanPrivacyActivity::class.java)
            }

            textViewSyaratKetentuan.setOnClickListener {
                Intent(context, SyaratKetentuanActivity::class.java).apply {
                    putExtra("from_profile", true)
                    startActivity(this)
                }
            }

            buttonKeluar.setOnClickListener {
                showDialogLogout()
            }

            textViewJPoint.setOnClickListener {
                activity?.openActivity(JempootPoinActivity::class.java)
            }

            textViewUlasanCustomer.setOnClickListener {
                activity?.openActivity(UlasanActivity::class.java)
            }

            textViewRating.setOnClickListener {
//                try {
//                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")))
//                } catch (e: ActivityNotFoundException) {
//                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")))
//                }
            }

            textViewBagikan.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Mr. Jempoot Driver")
                startActivity(shareIntent)
            }

            textViewBantuan.setOnClickListener {
                activity?.openActivity(PusatBantuanActivity::class.java)
            }

            textViewVerifikasiKesehatan.setOnClickListener {
                activity?.openActivity(VerifikasiKesahatanActivity::class.java)
            }

            textViewNotifikasi.setOnClickListener {
                activity?.openActivity(NotifikasiActivity::class.java)
            }

        }



    }

    private fun showProfile() {
        Glide.with(binding.circleImageView).load(BASE_URL_DRIVER_PHOTO + sessionManager.photo_profile)
            .transform(CenterCrop(), RoundedCorners(Constant.Companion.value.ROUND_IMAGE))
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

}