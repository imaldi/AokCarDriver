package com.surelabsid.mrjempootdriver.repository

import com.surelabsid.mrjempootdriver.network.ApiService
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseHome
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseDelNotifikasi
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseNotifikasi
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRekBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseListBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryNotifikasi @Inject constructor(
    private val api: ApiService,
    private val compositeDisposable: CompositeDisposable
) {

    fun repoGetNotif(
        driver_id: String,
        response_handler: (ResponseNotifikasi) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiGetNotif(driver_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoDelNotif(
        driver_id: String,
        response_handler: (ResponseDelNotifikasi) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiDelNotif(driver_id).subscribeOn(Schedulers.io())
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