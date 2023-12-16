package com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryTransaction
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.room.model.EntityOrderan
import com.surelabsid.mrjempootdriver.room.repository.RepositoryLocalOrderan
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseHome
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.RequestStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseService
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseStatusTransaction
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseTransactionByService
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseHealthDriver
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.modelrequest.RequestHealthVerification
import com.surelabsid.mrjempootdriver.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifikasiKesehatanViewModel @Inject constructor(
    val repositoryUser: RepositoryUser,
    val application: Application
) : ViewModel() {

    private val _health_verification = MutableLiveData<ResponseReqTopup>()
    val health_verification: LiveData<ResponseReqTopup> = _health_verification

    private val _health_driver = MutableLiveData<ResponseHealthDriver>()
    val health_driver: LiveData<ResponseHealthDriver> = _health_driver

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun healthVerification(param: RequestHealthVerification) {
        _loading.postValue(true)
        repositoryUser.repoHealthVerification(param, {
            _loading.postValue(false)
            _health_verification.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun healthDriver(driver_id: String) {
        _loading.postValue(true)
        repositoryUser.repoHealthDriver(driver_id, {
            _loading.postValue(false)
            _health_driver.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun onClear() {
        repositoryUser.onClear()
    }
}