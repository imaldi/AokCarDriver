package com.surelabsid.mrjempootdriver.ui.pendaftaran

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.databinding.ActivityLoginVerifikasiBinding
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseRequestOtp
import com.surelabsid.mrjempootdriver.ui.login.viewmodel.LoginViewModel
import com.surelabsid.mrjempootdriver.ui.lupapassword.ResetPasswordActivity
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseStatusPendaftaran
import com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel.PendaftaranViewModel
import com.surelabsid.mrjempootdriver.ui.pendaftaranselesai.BuatPasswordActivity
import com.surelabsid.mrjempootdriver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

const val START_MILLI_SECONDS = 30000L

@AndroidEntryPoint
class LoginVerifikasiActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginVerifikasiBinding

    lateinit var viewModel: PendaftaranViewModel

    lateinit var viewModelLogin: LoginViewModel

    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false
    var time_in_milli_seconds = 30000L

    private var status_pedaftaran: String? = null

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PendaftaranViewModel::class.java)
        viewModelLogin = ViewModelProvider(this).get(LoginViewModel::class.java)

        status_pedaftaran = intent.getStringExtra("status_pendaftaran")

        initView()
        attachObserve()

    }

    private fun resetTimer() {
        time_in_milli_seconds = START_MILLI_SECONDS
        updateTextUI()
    }

    private fun updateTextUI() {
        var minute = (time_in_milli_seconds / 1000) / 60
        var seconds = (time_in_milli_seconds / 1000) % 60

        minute = if (minute < 10) "0$minute".toLong() else minute
        seconds = if (seconds < 10) "0$seconds".toLong() else seconds

        binding.textViewMohonTunggu.text = "Mohon tunggu dalam $minute:$seconds"
    }

    private fun attachObserve() {
        with(viewModel) {

        }

        with(viewModelLogin) {
            request_otp.observe(this@LoginVerifikasiActivity, { responseRequestOtp(it) })
            verify_otp.observe(this@LoginVerifikasiActivity, { responseVerifyOtp(it) })
            message.observe(this@LoginVerifikasiActivity, { binding.root.showSnackbar(it, Snackbar.LENGTH_LONG) })
            error.observe(this@LoginVerifikasiActivity, { showError(this@LoginVerifikasiActivity, it) })
        }
    }

    private fun responseVerifyOtp(it: ResponseRequestOtp?) {
        // ini mengambil status pendaftaran dari nomor tertentu, kalau masih nunggu verif, dialihkan ke "silahkan menunggu verifikasi"
        // kalau sudah di verifikasi, akan ada otp masuk (saat ini 6 digit, nanti tanyakan gimana maunya, 6 apa 4 digit)
        // verifikasi otp nya ini tergantung dari status pendaftaran nomor yang didaftarkan, kalau 1, masuk ke create password
        // kalau selain dari itu, bakal masuk ke jenis transport dll
        if (it?.code == 200 || it?.message?.lowercase()?.contains("verified") == true) {
            if (status_pedaftaran == "0") {
                openActivity(PendaftaranVerifikasiActivity::class.java)
            }
            else if (status_pedaftaran == "1") {
                sessionManager.flag_create_password = true
                openActivity(BuatPasswordActivity::class.java)
            }
            else if (sessionManager.flag_reset_password) {
                openActivity(ResetPasswordActivity::class.java)
            } else {
                moveActivity(JenisTransportActivity::class.java)
            }
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseRequestOtp(it: ResponseRequestOtp?) {
        if (it?.code == 201) {
            binding.root.showSnackbar("${it.message}", Snackbar.LENGTH_SHORT)
//            binding.root.showSnackbar("${it.message} ${it.otp}", Snackbar.LENGTH_SHORT)
        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }


    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
//                loadConfeti()
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI()
            }
        }
        countdown_timer.start()

        isRunning = true

    }

    private fun initView() {

        startTimer(time_in_milli_seconds)

        if (!sessionManager.is_dev) {
            viewModelLogin.requestOtp("${sessionManager.dial_code.substring(1)}${sessionManager.username}")
        }

        binding.textViewKirimUlang.setOnClickListener {
            viewModelLogin.requestOtp("${sessionManager.dial_code.substring(1)}${sessionManager.username}")
        }

        binding.imageButtonBack.setOnClickListener {
            sessionManager.flag_create_password = false
            onBackPressed()
        }

        binding.textViewNomorHandphone.text = "${sessionManager.dial_code}${sessionManager.username}"

        binding.buttonVerifikasi.setOnClickListener {


//            /// FIXME where is is_dev set, maybe need to remove this later
            // kalau status app dev. kalau status nomor nya 0, kasih ke alur 0, kalau 1, buat password, selain dari itu, arahkan ke form selanjutnya
            // sekarang ini masih ga tau gimana ngatur .isdev nya => ubah aja d class declaration nya value default nya
            if (sessionManager.is_dev) {
                if (status_pedaftaran == "0") {
                    openActivity(PendaftaranVerifikasiActivity::class.java)
                }
                else if (status_pedaftaran == "1") {
                    sessionManager.flag_create_password = true
                    openActivity(BuatPasswordActivity::class.java)
                } else if (sessionManager.flag_reset_password) {
                    openActivity(ResetPasswordActivity::class.java)
                } else {
                    moveActivity(JenisTransportActivity::class.java)
                }
            } else {
                // here is the code to check the code
                val kode_otp = "${binding.numone.text}${binding.numtwo.text}${binding.numthree.text}${binding.numfour.text}"
                viewModelLogin.verifyOtp("${sessionManager.dial_code.substring(1)}${sessionManager.username}", kode_otp)
            }
        }

        binding.numone.doOnTextChanged { text, start, before, count ->
            when(count){
                1 -> {
                    binding.numtwo.requestFocus()
                    binding.numtwo.selectAll()
                }
            }
        }

        binding.numtwo.doOnTextChanged { text, start, before, count ->
            when(count){
                0 -> {
                    binding.numone.requestFocus()
                    binding.numone.selectAll()
                }
                1 -> {
                    binding.numthree.requestFocus()
                    binding.numthree.selectAll()

                }
            }
        }

        binding.numthree.doOnTextChanged { text, start, before, count ->
            when(count){
                0 -> {
                    binding.numtwo.requestFocus()
                    binding.numtwo.selectAll()
                }
                1 -> {
                    binding.numfour.requestFocus()
                    binding.numfour.selectAll()
                }
            }
        }

        binding.numfour.doOnTextChanged { text, start, before, count ->
            when(count){
                0 -> {
                    binding.numthree.requestFocus()
                    binding.numthree.selectAll()
                }
                1 -> {
//                    binding.numfive.requestFocus()
//                    binding.numfive.selectAll()
                }
            }
        }

//        binding.numfive.doOnTextChanged { text, start, before, count ->
//            when(count){
//                0 -> {
//                    binding.numfour.requestFocus()
//                    binding.numfour.selectAll()
//                }
//                1 -> {
//                    binding.numsix.requestFocus()
//                    binding.numsix.selectAll()
//                }
//            }
//        }

//        binding.numsix.doOnTextChanged { text, start, before, count ->
//            when(count){
//                0 -> {
//                    binding.numfive.requestFocus()
//                    binding.numfive.selectAll()
//                }
//                1 -> {
//                    //
//                }
//            }
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        sessionManager.flag_create_password = false
    }

    override fun onDestroy() {
        super.onDestroy()
        sessionManager.flag_create_password = false
    }
}