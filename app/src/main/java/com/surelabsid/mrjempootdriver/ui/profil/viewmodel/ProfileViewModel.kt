package com.surelabsid.mrjempootdriver.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogout
import com.surelabsid.mrjempootdriver.ui.lupapassword.modelrequest.RequestResetPassword
import com.surelabsid.mrjempootdriver.ui.profil.modelrequest.*
import com.surelabsid.mrjempootdriver.ui.profil.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val repositoryUser: RepositoryUser) : ViewModel() {

    private val _logout = MutableLiveData<ResponseLogout>()
    val logout: LiveData<ResponseLogout> = _logout

    private val _edit_profile = MutableLiveData<ResponseEditProfile>()
    val edit_profile: LiveData<ResponseEditProfile> = _edit_profile

    private val _change_password = MutableLiveData<ResponseEditProfile>()
    val change_password: LiveData<ResponseEditProfile> = _change_password

    private val _reset_password = MutableLiveData<ResponseReqTopup>()
    val reset_password: LiveData<ResponseReqTopup> = _reset_password

    private val _change_phone_driver = MutableLiveData<ResponseChangePhoneDriver>()
    val change_phone_driver: LiveData<ResponseChangePhoneDriver> = _change_phone_driver

    private val _send_email_verify = MutableLiveData<ResponseSendEmailVerify>()
    val send_email_verify: LiveData<ResponseSendEmailVerify> = _send_email_verify

    private val _change_email_driver = MutableLiveData<ResponseChangeEmailDriver>()
    val change_email_driver: LiveData<ResponseChangeEmailDriver> = _change_email_driver

    private val _check_email_driver = MutableLiveData<ResponseCheckEmailDriver>()
    val check_email_driver: LiveData<ResponseCheckEmailDriver> = _check_email_driver

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun logout(param: RequestLogout?) {
        _loading.postValue(true)
        repositoryUser.repoLogout(param, {
            _loading.postValue(false)
            _logout.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun editProfile(param: RequestEditProfile?) {
        _loading.postValue(true)
        repositoryUser.repoEditProfile(param, {
            _loading.postValue(false)
            _edit_profile.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun changePassword(param: RequestChangePassword?) {
        _loading.postValue(true)
        repositoryUser.repoChangePassword(param, {
            _loading.postValue(false)
            _change_password.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun resetPassword(param: RequestResetPassword) {
        _loading.postValue(true)
        repositoryUser.repoResetPassword(param, {
            _loading.postValue(false)
            _reset_password.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun changePhoneDriver(param: RequestChangePhoneDriver) {
        _loading.postValue(true)
        repositoryUser.repoChangePhoneDriver(param, {
            _loading.postValue(false)
            _change_phone_driver.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun changeEmailDriver(param: RequestChangeEmailDriver) {
        _loading.postValue(true)
        repositoryUser.repoChangeEmailDriver(param, {
            _loading.postValue(false)
            _change_email_driver.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun checkEmailDriver(param: RequestCheckEmailDriver) {
        _loading.postValue(true)
        repositoryUser.repoCheckEmailDriver(param, {
            _loading.postValue(false)
            _check_email_driver.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun sendEmailVerify(param: RequestSendEmailVerify?) {
        _loading.postValue(true)
        repositoryUser.repoSendEmailVerify(param, {
            _loading.postValue(false)
            _send_email_verify.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

}