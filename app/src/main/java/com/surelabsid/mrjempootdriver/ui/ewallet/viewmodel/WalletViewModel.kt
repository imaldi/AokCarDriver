package com.surelabsid.mrjempootdriver.ui.ewallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.repository.RepositoryWallet
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseHome
import com.surelabsid.mrjempootdriver.ui.ewallet.modelrequest.RequestWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseWithdraw
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    val repositoryWallet: RepositoryWallet,
    val application: Application
) : ViewModel() {

    private val _history_wallet = MutableLiveData<ResponseHistoryWallet>()
    val history_wallet: LiveData<ResponseHistoryWallet> = _history_wallet

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

    fun historyWallet(driver_id: String?) {
        _loading.postValue(true)
        repositoryWallet.repoHistoryWallet(driver_id ?: "", {
            if (it.code == "200") {
                _loading.postValue(false)
                _history_wallet.postValue(it)
            }
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun historyWalletByDate(driver_id: String?, startdate: String?, enddate: String?) {
        _loading.postValue(true)
        _is_connected.postValue(true)
        repositoryWallet.repoHistoryWalletByDate(
            driver_id ?: "",
            startdate ?: "",
            enddate ?: "",
            {
                if (it.code == "200") {
                    _loading.postValue(false)
                    _history_wallet.postValue(it)
                }
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })
    }

    fun reqTopup(param: RequestReqTopup?) {
        _loading.postValue(true)
        repositoryWallet.repoReqTopup(param, {
            if (it.code == "200") {
                _loading.postValue(false)
                _req_topup.postValue(it)
            }
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun reqWithdraw(param: RequestWithdraw?) {
        _loading.postValue(true)
        repositoryWallet.repoWithdraw(param, {
            _loading.postValue(false)
            _req_withdraw.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }


}