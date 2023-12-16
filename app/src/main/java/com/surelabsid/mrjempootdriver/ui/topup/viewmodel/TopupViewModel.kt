package com.surelabsid.mrjempootdriver.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryWallet
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopupViewModel @Inject constructor(val repositoryWallet: RepositoryWallet) : ViewModel() {

    private val _req_top_up = MutableLiveData<ResponseReqTopup>()
    val req_top_up: LiveData<ResponseReqTopup> = _req_top_up

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun reqTopup(param: RequestReqTopup?) {
        _loading.postValue(true)
        repositoryWallet.repoReqTopup(param, {
            _loading.postValue(false)
            _req_top_up.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

}