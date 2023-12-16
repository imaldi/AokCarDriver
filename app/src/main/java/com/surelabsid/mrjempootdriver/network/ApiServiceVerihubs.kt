package com.surelabsid.mrjempootdriver.network

import com.surelabsid.mrjempootdriver.request_model.VerihubsRequestBody
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestTurningOn
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseAppSettings
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseTurningOn
import com.surelabsid.mrjempootdriver.ui.ewallet.modelrequest.RequestWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseWithdraw
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestPostPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestRedeemPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponsePoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponseSouvenir
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.RequestChangePassword
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.RequestEditProfile
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogout
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseRequestOtp
import com.surelabsid.mrjempootdriver.ui.lupapassword.modelrequest.RequestResetPassword
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelrequest.RequestPostNotif
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseDelNotifikasi
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseNotifikasi
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.*
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRekBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseListBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRelationship
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseStatusPendaftaran
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.RequestSendEmailVerify
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseEditProfile
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseLogout
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseSendEmailVerify
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.ResponsePerformanceDriver
import com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.modelrequest.RequestHealthVerification
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiServiceVerihubs {

    @POST("v1/otp/send")
    fun apiSetOtp(
        @Body verihubsRequestBody: VerihubsRequestBody
    ): Single<ResponseRequestOtp>

    @POST("v1/otp/verify")
    fun apiVerifyOtp(
        @Body verihubsRequestBody: VerihubsRequestBody
    ): Single<ResponseRequestOtp>

}