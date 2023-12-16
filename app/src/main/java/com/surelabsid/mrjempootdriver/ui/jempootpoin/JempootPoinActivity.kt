package com.surelabsid.mrjempootdriver.ui.jempootpoin

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityJempootPoinBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.jempootpoin.adapter.PoinAdapter
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponsePoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.viewmodel.JempootPoinViewModel
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_POIN
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_DRIVER_PHOTO
import com.surelabsid.mrjempootdriver.utils.hide
import com.surelabsid.mrjempootdriver.utils.openActivity
import com.surelabsid.mrjempootdriver.utils.showSnackbar

class JempootPoinActivity : BaseActivity() {

    lateinit var binding: ActivityJempootPoinBinding

    private lateinit var viewModel: JempootPoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJempootPoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(JempootPoinViewModel::class.java)

        initView()

        attachObserve()


    }

    private fun attachObserve() {
        with(viewModel) {
            poin.observe(this@JempootPoinActivity, { responsePoin(it) })
        }
    }

    private fun responsePoin(it: ResponsePoin?) {
        if (it?.code == "200") {

            var total_poin = 0
            if (it.data?.balancePoin?.total != null) {
                total_poin = it.data?.balancePoin?.total.toInt()
            }

            binding.textPoin.text = "Total Poin : ${total_poin} POIN"

            sessionManager.poin = total_poin.toString()

            val adapter = PoinAdapter(this, it.data?.walletPoin)
            adapter.setCurrentViewType(ITEM_POIN)

            binding.recyclerView5.adapter = adapter
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }

    }

    private fun initView() {

        showProfile()

        viewModel.totalPoin(sessionManager.id)

        with(binding) {
            textName.text = sessionManager.full_name
            buttonRedeemPoin.setOnClickListener {
                openActivity(RadeemPoinActivity::class.java)
            }

            imageButtonBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun showProfile() {
        Glide.with(binding.imageViewFotoProfil).load(BASE_URL_DRIVER_PHOTO + sessionManager.photo_profile)
            .transform(CenterCrop(), RoundedCorners(Constant.Companion.value.ROUND_IMAGE))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
//                    binding.progressBarImage.hide()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
//                    binding.progressBarImage.hide()
                    return false
                }

            })
            .error(R.drawable.bg_beranda).into(binding.imageViewFotoProfil)
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

}