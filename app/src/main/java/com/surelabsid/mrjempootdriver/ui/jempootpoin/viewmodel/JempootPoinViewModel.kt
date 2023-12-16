package com.surelabsid.mrjempootdriver.ui.jempootpoin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryPoin
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.repository.RepositoryWallet
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseHome
import com.surelabsid.mrjempootdriver.ui.ewallet.modelrequest.RequestWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseWithdraw
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestPostPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestRedeemPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponsePoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponseSouvenir
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JempootPoinViewModel @Inject constructor(
    val repositoryPoin: RepositoryPoin,
    val application: Application
) : ViewModel() {

    private val _poin = MutableLiveData<ResponsePoin>()
    val poin: LiveData<ResponsePoin> = _poin

    private val _post_poin = MutableLiveData<ResponsePoin>()
    val post_poin: LiveData<ResponsePoin> = _post_poin

    private val _souvenir = MutableLiveData<ResponseSouvenir>()
    val souvenir: LiveData<ResponseSouvenir> = _souvenir

    private val _redeem_poin = MutableLiveData<ResponsePoin>()
    val redeem_poin: LiveData<ResponsePoin> = _redeem_poin

    private val _req_topup = MutableLiveData<ResponseReqTopup>()
    val req_topup: LiveData<ResponseReqTopup> = _req_topup

    private val _req_withdraw = MutableLiveData<ResponseWithdraw>()
    val req_withdraw: LiveData<ResponseWithdraw> = _req_withdraw

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _is_connected = MutableLiveData<Boolean>()
    val is_connected: LiveData<Boolean> = _is_connected

    fun totalPoin(driver_id: String?) {
        _loading.postValue(true)
        repositoryPoin.repoPoin(driver_id ?: "", {
            _loading.postValue(false)
            _poin.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun postPoin(param: RequestPostPoin) {
        _loading.postValue(true)
        repositoryPoin.repoPostPoin(param, {
            _loading.postValue(false)
            _post_poin.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun listSouvenir() {
        _loading.postValue(true)
        repositoryPoin.repoSouvenir({
            _loading.postValue(false)
            _souvenir.postValue(it)

        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun redeemPoin(param: RequestRedeemPoin?) {
        _loading.postValue(true)
        repositoryPoin.repoRedeemPoin(param, {
            _loading.postValue(false)
            _redeem_poin.postValue(it)

        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

}