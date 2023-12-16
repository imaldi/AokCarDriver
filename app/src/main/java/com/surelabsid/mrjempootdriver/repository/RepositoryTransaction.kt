package com.surelabsid.mrjempootdriver.repository

import com.surelabsid.mrjempootdriver.network.ApiService
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.*
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.ResponsePerformanceDriver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryTransaction @Inject constructor(
    private val api: ApiService,
    private val compositeDisposable: CompositeDisposable
) {

    fun repoGetService(
        response_handler: (ResponseService) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiGetService().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoPerformanceDriver(
        driver_id: String,
        response_handler: (ResponsePerformanceDriver) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiGetPerformance(driver_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoTransactionByService(
        driver_id: String,
        service_id: Int,
        response_handler: (ResponseTransactionByService) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiTransactionByService(driver_id, service_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoTransactionByServiceByStatus(
        driver_id: String,
        service_id: Int,
        transaction_status: Int,
        response_handler: (ResponseTransactionByService) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiTransactionByServiceByStatus(driver_id, service_id, transaction_status).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoStatusTransaction(
        param: RequestStatusTransaction,
        response_handler: (ResponseStatusTransaction) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiStatusTransaction(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoBayarKeAokCar(
        param: RequestBayarKeAokCar,
        response_handler: (ResponseBayar) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiBayarKeAokCar(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }

    fun repoBayarKeMerchant(
        param: RequestBayarKeMerchant,
        response_handler: (ResponseBayar) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiBayarKeMerchant(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }
    fun repoVerifyCodeMerchant(
        param: RequestVerifyCodeMerchant,
        response_handler: (ResponseStatusTransaction) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiVerifyCodeMerchant(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }
    fun repoSendStruk(
        param: RequestSendStruk,
        response_handler: (ResponseStatusTransaction) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiSendStruk(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }
    fun repoSendBuktiTerimaPaket(
        param: RequestSendBuktiTerimaPaket,
        response_handler: (ResponseStatusTransaction) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiSendBuktiTerimaPaket(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    response_handler(it)
                }, {
                    error_handler(it)
                })
        )

    }
    fun repoNilaiCustomer(
        param: RequestNilaiCustomer,
        response_handler: (ResponseNilaiCustomer) -> Unit,
        error_handler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(
            api.apiNilaiCustomer(param).subscribeOn(Schedulers.io())
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