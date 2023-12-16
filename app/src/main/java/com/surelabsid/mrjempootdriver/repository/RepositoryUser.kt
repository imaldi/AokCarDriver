package com.surelabsid.mrjempootdriver.repository

import com.surelabsid.mrjempootdriver.network.ApiService
import com.surelabsid.mrjempootdriver.network.ApiServiceVerihubs
import com.surelabsid.mrjempootdriver.request_model.VerihubsRequestBody
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestChangeStatusDriver
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestTurningOn
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestUpdateLocation
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelrequest.RequestSendChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelrequest.RequestStartChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseGetChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseSendChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseStartChatCS
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogout
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseRequestOtp
import com.surelabsid.mrjempootdriver.ui.lupapassword.modelrequest.RequestResetPassword
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseDelNotifikasi
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseStatusPendaftaran
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.*
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.modelrequest.RequestHealthVerification
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryUser @Inject constructor(
    private val api: ApiService,
    private val apiVerihubs: ApiServiceVerihubs,
    private val compositeDisposable: CompositeDisposable
) {

    fun repoLogin(
        param: RequestLogin?,
        response_handler: (ResponseLogin) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiLogin(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoLogout(
        param: RequestLogout?,
        response_handler: (ResponseLogout) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiLogout(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoHome(
        param: RequestHome?,
        response_handler: (ResponseHome) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiHome(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoTurningOn(
        param: RequestTurningOn?,
        response_handler: (ResponseTurningOn) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiTurningOn(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoEditProfile(
        param: RequestEditProfile?,
        response_handler: (ResponseEditProfile) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiEditProfile(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoChangePassword(
        param: RequestChangePassword?,
        response_handler: (ResponseEditProfile) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiChangePassword(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoRegisterDriver(
        param: RequestRegisterDriver?,
        response_handler: (ResponseRegisterDriver) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiRegisterDriver(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoSendEmailVerify(
        param: RequestSendEmailVerify?,
        response_handler: (ResponseSendEmailVerify) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiSendEmailVerify(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoHealthVerification(
        param: RequestHealthVerification,
        response_handler: (ResponseReqTopup) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiHealthVerification(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoHealthDriver(
        driver_id: String,
        response_handler: (ResponseHealthDriver) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiHealthDriver(driver_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoResetPassword(
        param: RequestResetPassword,
        response_handler: (ResponseReqTopup) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiResetPassword(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoChangePhoneDriver(
        param: RequestChangePhoneDriver,
        response_handler: (ResponseChangePhoneDriver) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiChangePhoneDriver(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoChangeEmailDriver(
        param: RequestChangeEmailDriver,
        response_handler: (ResponseChangeEmailDriver) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiChangeEmailDriver(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoCheckEmailDriver(
        param: RequestCheckEmailDriver,
        response_handler: (ResponseCheckEmailDriver) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiCheckEmailDriver(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoStatusPendaftaran(
        phone_number: String,
        response_handler: (ResponseStatusPendaftaran) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiStatusPendaftaran(phone_number).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoChangeStatusDriver(
        param: RequestChangeStatusDriver,
        response_handler: (ResponseChangeStatusDriver) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiChangeStatusDriver(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoRequestOtp(
        phone_number: String,
        response_handler: (ResponseRequestOtp) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        val randomNumber = (0 until 9999).random()
        compositeDisposable.add(
            apiVerihubs.apiSetOtp(VerihubsRequestBody(phone_number,otp = randomNumber.toString())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoVerifyOtp(
        phone_number: String,
        kode: String,
        response_handler: (ResponseRequestOtp) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            apiVerihubs.apiVerifyOtp(VerihubsRequestBody(phone_number, kode)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoStartChatCS(
        param: RequestStartChatCS,
        response_handler: (ResponseStartChatCS) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiStartChatCS(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoSendChatCS(
        param: RequestSendChatCS,
        response_handler: (ResponseSendChatCS) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiSendChatCS(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoGetChatCS(
        id_message: String,
        response_handler: (ResponseGetChatCS) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiGetChatCS(id_message).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoUpdateLocation(
        param: RequestUpdateLocation,
        response_handler: (ResponseUpdateLocation) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiUpdateLocation(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoLocationAllDriver(
        response_handler: (ResponseLocationAllDriver) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiLocationAllDriver().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }


    fun onClear() {
        compositeDisposable.clear()
    }


}