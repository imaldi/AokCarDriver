package com.surelabsid.mrjempootdriver.repository

import com.surelabsid.mrjempootdriver.network.ApiService
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestPostPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestRedeemPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponsePoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponseSouvenir
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryPoin @Inject constructor(
    private val api: ApiService,
    private val compositeDisposable: CompositeDisposable
) {

    fun repoPoin(
        driver_id: String?,
        response_handler: (ResponsePoin) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiPoin(driver_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoPostPoin(
        param: RequestPostPoin,
        response_handler: (ResponsePoin) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiPostPoin(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoSouvenir(
        response_handler: (ResponseSouvenir) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiSouvenir().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoRedeemPoin(
        param: RequestRedeemPoin?,
        response_handler: (ResponsePoin) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiRedeemPoin(param).subscribeOn(Schedulers.io())
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