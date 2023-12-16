package com.surelabsid.mrjempootdriver.repository

import com.surelabsid.mrjempootdriver.network.ApiService
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseHome
import com.surelabsid.mrjempootdriver.ui.ewallet.modelrequest.RequestWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseWithdraw
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogout
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseLogout
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryWallet @Inject constructor(
    private val api: ApiService,
    private val compositeDisposable: CompositeDisposable
) {

    fun repoReqTopup(
        param: RequestReqTopup?,
        response_handler: (ResponseReqTopup) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiReqTopup(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoHistoryWallet(
        driver_id: String,
        response_handler: (ResponseHistoryWallet) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiHistoryWallet(driver_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoHistoryWalletByDate(
        driver_id: String,
        startdate: String,
        enddate: String,
        response_handler: (ResponseHistoryWallet) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiHistoryWalletByDate(driver_id, startdate, enddate).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoWithdraw(
        param: RequestWithdraw?,
        response_handler: (ResponseWithdraw) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiWithdraw(param).subscribeOn(Schedulers.io())
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