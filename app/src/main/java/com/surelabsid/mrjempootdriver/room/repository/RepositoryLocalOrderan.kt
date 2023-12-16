package com.surelabsid.mrjempootdriver.room.repository

import com.surelabsid.mrjempootdriver.room.Database
import com.surelabsid.mrjempootdriver.room.model.EntityOrderan
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryLocalOrderan @Inject constructor(
    private val database: Database,
    private val compositeDisposable: CompositeDisposable
) {

    fun insertOrderan(
        entityCart: EntityOrderan,
        responseHandler: (Boolean) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(Observable.fromCallable {
            database.daoOrderan().insertOrderan(entityCart)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(true)
            }, {
                errorHandler(it)
            }))
    }

    fun getOrderan(
        responseHandler: (List<EntityOrderan>) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(Observable.fromCallable {
            database.daoOrderan().getOrderan()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            }, {
                errorHandler(it)
            }))
    }

    fun getOrderanById(
        id_orderan: Int,
        responseHandler: (List<EntityOrderan>) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(Observable.fromCallable {
            database.daoOrderan().getOrderanById(id_orderan)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            }, {
                errorHandler(it)
            }))
    }

    fun updateButtonName(
        id_orderan: Int,
        button_name: String,
        responseHandler: (Boolean) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(Observable.fromCallable {
            database.daoOrderan().updateButtonName(id_orderan, button_name)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(true)
            },{
                errorHandler(it)
            }))
    }

    fun deleteOrderan(
        id_orderan: Int,
        responseHandler: (Boolean) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        compositeDisposable.add(Observable.fromCallable {
            database.daoOrderan().deleteOrderan(id_orderan)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(true)
            },{
                errorHandler(it)
            }))
    }

    fun deleteOrderanAll() {
        compositeDisposable.add(Observable.fromCallable {
            database.daoOrderan().deleteOrderanAll()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }
}