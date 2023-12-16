package com.surelabsid.mrjempootdriver.ui.pendaftaran.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryBank
import com.surelabsid.mrjempootdriver.repository.RepositoryRelationship
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelrequest.RequestRekBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseListBank
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRegisterDriver
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseRelationship
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.ResponseStatusPendaftaran
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PendaftaranViewModel @Inject constructor(
    val repositoryUser: RepositoryUser,
    val repositoryBank: RepositoryBank,
    val repositoryRelationship: RepositoryRelationship
) : ViewModel() {

    private val _register_driver = MutableLiveData<ResponseRegisterDriver>()
    val register_driver: LiveData<ResponseRegisterDriver> = _register_driver

    private val _list_bank = MutableLiveData<ResponseListBank>()
    val list_bank: LiveData<ResponseListBank> = _list_bank

    private val _rek_bank = MutableLiveData<ResponseListBank>()
    val rek_bank: LiveData<ResponseListBank> = _rek_bank

    private val _status_pendaftaran = MutableLiveData<ResponseStatusPendaftaran>()
    val status_pendaftaran: LiveData<ResponseStatusPendaftaran> = _status_pendaftaran

    private val _list_relationship = MutableLiveData<ResponseRelationship>()
    val list_relationship: LiveData<ResponseRelationship> = _list_relationship

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun registerDriver(param: RequestRegisterDriver?) {
        _loading.postValue(true)
        repositoryUser.repoRegisterDriver(param, {
            _loading.postValue(false)
            _register_driver.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun listBank() {
        _loading.postValue(true)
        repositoryBank.repoListBank({
            _loading.postValue(false)
            _list_bank.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun listRelationship() {
        repositoryRelationship.repoListRelationship({
            _list_relationship.postValue(it)
        }, {
            _error.postValue(it)
        })
    }

    fun rekBank(param: RequestRekBank?) {
        _loading.postValue(true)
        repositoryBank.repoRekBank(param, {
            _loading.postValue(false)
            _rek_bank.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun statusPendaftaran(phone_number: String) {
        _loading.postValue(true)
        repositoryUser.repoStatusPendaftaran(phone_number, {
            _loading.postValue(false)
            val statusValue = it.data?.status ?: "null"
            Log.d("statusValue register:", statusValue)
            _status_pendaftaran.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }



}