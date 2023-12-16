package com.surelabsid.mrjempootdriver.network

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
import com.surelabsid.mrjempootdriver.ui.ewallet.modelrequest.RequestWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseWithdraw
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestPostPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestRedeemPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponsePoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponseSouvenir
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogout
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
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
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.*
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.ResponsePerformanceDriver
import com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.modelrequest.RequestHealthVerification
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiService {

    @POST("driver/login")
    fun apiLogin(@Body param: RequestLogin?): Single<ResponseLogin>

    @POST("driver/logout")
    fun apiLogout(@Body param: RequestLogout?): Single<ResponseLogout>

    @POST("driver/home")
    fun apiHome(@Body param: RequestHome?): Single<ResponseHome>

    @POST("driver/turning_on")
    fun apiTurningOn(@Body param: RequestTurningOn?): Single<ResponseTurningOn>

    @POST("driver/edit_profile")
    fun apiEditProfile(@Body param: RequestEditProfile?): Single<ResponseEditProfile>

    @POST("driver/changepass")
    fun apiChangePassword(@Body param: RequestChangePassword?): Single<ResponseEditProfile>

    @POST("authapi/register_driver")
    fun apiRegisterDriver(@Body param: RequestRegisterDriver?): Flowable<ResponseRegisterDriver>

    @POST("authapi/send_email_verify")
    fun apiSendEmailVerify(@Body param: RequestSendEmailVerify?): Single<ResponseSendEmailVerify>

    @GET("transactionapi/listBank")
    fun apiListBank(): Flowable<ResponseListBank>

    @GET("transactionapi/service")
    fun apiGetService(): Flowable<ResponseService>

    @GET("transactionapi/performance_driver/{driver_id}")
    fun apiGetPerformance(@Path("driver_id") driver_id: String): Flowable<ResponsePerformanceDriver>

    @GET("transactionapi/transaction/{driver_id}/{service_id}")
    fun apiTransactionByService(
        @Path("driver_id") driver_id: String,
        @Path("service_id") service_id: Int
    ): Flowable<ResponseTransactionByService>

    @GET("transactionapi/transaction/{driver_id}/{service_id}/status/{transaction_status}")
    fun apiTransactionByServiceByStatus(
        @Path("driver_id") driver_id: String,
        @Path("service_id") service_id: Int,
        @Path("transaction_status") transaction_status: Int
    ): Flowable<ResponseTransactionByService>

    @POST("transactionapi/notif")
    fun apiPostNotif(
        @Body param: RequestPostNotif
    ): Single<ResponseDelNotifikasi>

    @GET("transactionapi/notif/{driver_id}")
    fun apiGetNotif(
        @Path("driver_id") driver_id: String
    ): Flowable<ResponseNotifikasi>

    @DELETE("transactionapi/notif/{driver_id}")
    fun apiDelNotif(
        @Path("driver_id") driver_id: String
    ): Single<ResponseDelNotifikasi>

    @POST("transactionapi/statustrx")
    fun apiStatusTransaction(
        @Body param: RequestStatusTransaction
    ): Single<ResponseStatusTransaction>

    @POST("transactionapi/val_cus_trans")
    fun apiNilaiCustomer(
        @Body param: RequestNilaiCustomer
    ): Single<ResponseNilaiCustomer>

    @POST("transactionapi/pay_to_jempoot")
    fun apiBayarKeAokCar(
        @Body param: RequestBayarKeAokCar
    ): Single<ResponseBayar>

    @POST("transactionapi/pay_to_merchant")
    fun apiBayarKeMerchant(
        @Body param: RequestBayarKeMerchant
    ): Single<ResponseBayar>

    @POST("transactionapi/ver_code_merchant")
    fun apiVerifyCodeMerchant(
        @Body param: RequestVerifyCodeMerchant
    ): Single<ResponseStatusTransaction>

    @POST("transactionapi/send_struk")
    fun apiSendStruk(
        @Body param: RequestSendStruk
    ): Single<ResponseStatusTransaction>

    @POST("transactionapi/receive_packet")
    fun apiSendBuktiTerimaPaket(
        @Body param: RequestSendBuktiTerimaPaket
    ): Single<ResponseStatusTransaction>

    @GET("transactionapi/app_cost")
    fun apiAppCost(): Single<ResponseAppCost>

    @GET("walletapi/wallethistory/{driver_id}")
    fun apiHistoryWallet(@Path("driver_id") driver_id: String): Flowable<ResponseHistoryWallet>

    @GET("walletapi/wallethistory/{driver_id}/{startdate}/{enddate}")
    fun apiHistoryWalletByDate(
        @Path("driver_id") driver_id: String,
        @Path("startdate") startdate: String,
        @Path("enddate") enddate: String
    ): Flowable<ResponseHistoryWallet>

    @POST("walletapi/rekbank")
    fun apiRekBank(@Body param: RequestRekBank?): Flowable<ResponseListBank>

    @POST("walletapi/withdraw")
    fun apiWithdraw(@Body param: RequestWithdraw?): Single<ResponseWithdraw>

    @GET("walletapi/poin/{driver_id}")
    fun apiPoin(@Path("driver_id") driver_id: String?): Single<ResponsePoin>

    @POST("walletapi/poin")
    fun apiPostPoin(@Body param: RequestPostPoin?): Single<ResponsePoin>

    @POST("walletapi/redeempoin")
    fun apiRedeemPoin(@Body param: RequestRedeemPoin?): Single<ResponsePoin>

    @GET("walletapi/souvenir")
    fun apiSouvenir(): Flowable<ResponseSouvenir>

    @POST("wallet/reqTopup")
    fun apiReqTopup(@Body param: RequestReqTopup?): Flowable<ResponseReqTopup>


    @GET("profileapi/relationship")
    fun apiGetRelationship(): Flowable<ResponseRelationship>

    @GET("profileapi/health/{driver_id}")
    fun apiHealthDriver(@Path ("driver_id") driver_id: String): Single<ResponseHealthDriver>

    @POST("profileapi/health")
    fun apiHealthVerification(@Body param: RequestHealthVerification): Single<ResponseReqTopup>

    @POST("profileapi/changePasswordDriver")
    fun apiResetPassword(@Body param: RequestResetPassword): Single<ResponseReqTopup>

    @POST("profileapi/changePhoneDriver")
    fun apiChangePhoneDriver(@Body param: RequestChangePhoneDriver): Single<ResponseChangePhoneDriver>

    @POST("profileapi/changeEmailDriver")
    fun apiChangeEmailDriver(@Body param: RequestChangeEmailDriver): Single<ResponseChangeEmailDriver>

    @POST("profileapi/checkemail")
    fun apiCheckEmailDriver(@Body param: RequestCheckEmailDriver): Single<ResponseCheckEmailDriver>

    @GET("driver/statusPendaftaran/{phone_number}")
    fun apiStatusPendaftaran(@Path("phone_number") phone_number: String): Single<ResponseStatusPendaftaran>

    @GET("appsettings/getSyarat")
    fun apiAppSettings(): Single<ResponseAppSettings>

    @POST("profileapi/changeStatusDriver")
    fun apiChangeStatusDriver(@Body param: RequestChangeStatusDriver): Single<ResponseChangeStatusDriver>

    @POST("chatcs/startChat")
    fun apiStartChatCS(@Body param: RequestStartChatCS): Single<ResponseStartChatCS>

    @GET("chatcs/chat/{id_message}")
    fun apiGetChatCS(@Path("id_message") id_message: String?): Flowable<ResponseGetChatCS>

    @POST("chatcs/sendChat")
    fun apiSendChatCS(@Body param: RequestSendChatCS): Flowable<ResponseSendChatCS>

    @POST("driver/update_location")
    fun apiUpdateLocation(@Body param: RequestUpdateLocation): Single<ResponseUpdateLocation>

    @GET("customerapi/alldriver")
    fun apiLocationAllDriver() : Flowable<ResponseLocationAllDriver>
}