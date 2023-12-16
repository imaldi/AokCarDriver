package com.surelabsid.mrjempootdriver.ui.ulasan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryTransaction
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.RequestChangePassword
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.RequestEditProfile
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogout
import com.surelabsid.mrjempootdriver.ui.lupapassword.modelrequest.RequestResetPassword
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.RequestSendEmailVerify
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseEditProfile
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseLogout
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.ResponseSendEmailVerify
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse.ResponsePerformanceDriver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UlasanViewModel @Inject constructor(val repositoryTransaction: RepositoryTransaction) :
    ViewModel() {

    private val _performance_driver = MutableLiveData<ResponsePerformanceDriver>()
    val performance_driver: LiveData<ResponsePerformanceDriver> = _performance_driver

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun performanceDriver(driver_id: String) {
        _loading.postValue(true)
        repositoryTransaction.repoPerformanceDriver(driver_id, {
            _loading.postValue(false)
            _performance_driver.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

}