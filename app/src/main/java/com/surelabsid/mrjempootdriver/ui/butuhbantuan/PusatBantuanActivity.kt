package com.surelabsid.mrjempootdriver.ui.butuhbantuan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.databinding.ActivityPusatBantuanBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelrequest.RequestSendChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelrequest.RequestStartChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseStartChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.viewmodel.ButuhBantuanViewModel
import com.surelabsid.mrjempootdriver.utils.openActivity
import com.surelabsid.mrjempootdriver.utils.showError
import com.surelabsid.mrjempootdriver.utils.showSnackbar
import com.surelabsid.mrjempootdriver.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PusatBantuanActivity : BaseActivity() {

    lateinit var binding: ActivityPusatBantuanBinding

    lateinit var viewModel: ButuhBantuanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPusatBantuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ButuhBantuanViewModel::class.java)

        initView()

        attachObserve()
    }

    private fun attachObserve() {
        with(viewModel) {
            start_chat_cs.observe(this@PusatBantuanActivity, { responseStartChatCS(it) })
            error.observe(this@PusatBantuanActivity, { showError(this@PusatBantuanActivity, it) })
        }
    }

    private fun responseStartChatCS(it: ResponseStartChatCS?) {
        if (it?.code == "201") {

            sessionManager.id_message_cs = it.data?.idMessage ?: ""
            showToast(this, it.message)
            openActivity(ChatCSActivity::class.java)

        } else {
            binding.root.showSnackbar("${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun initView() {
        binding.imageButtonBack.setOnClickListener {
            finish()
        }

        binding.textViewFAQ.setOnClickListener {
            openActivity(FAQActivity::class.java)
        }

        binding.textViewChatCS.setOnClickListener {

            if (sessionManager.id_message_cs == "") {
                val param = RequestStartChatCS(sessionManager.id)
                viewModel.startChatCS(param)
            } else {
                openActivity(ChatCSActivity::class.java)
            }

        }
    }
}