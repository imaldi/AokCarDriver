package com.surelabsid.mrjempootdriver.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.ui.login.modelrequest.RequestLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseLogin
import com.surelabsid.mrjempootdriver.ui.login.modelresponse.ResponseRequestOtp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repositoryUser: RepositoryUser) : ViewModel() {

    private val _login = MutableLiveData<ResponseLogin>()
    val login: LiveData<ResponseLogin> = _login

    private val _request_otp = MutableLiveData<ResponseRequestOtp>()
    val request_otp: LiveData<ResponseRequestOtp> = _request_otp

    private val _verify_otp = MutableLiveData<ResponseRequestOtp>()
    val verify_otp: LiveData<ResponseRequestOtp> = _verify_otp

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun login(param: RequestLogin?) {
        _loading.postValue(true)
        repositoryUser.repoLogin(param, {
            _loading.postValue(false)
            _login.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun requestOtp(phone_number: String) {
        _loading.postValue(true)
        repositoryUser.repoRequestOtp(phone_number, {
            _loading.postValue(false)
            Log.d("OTP LoginViewModel",it.toString() );
            _request_otp.postValue(it)
        }, {

            if (it is HttpException) {
//                val handleMessage = ResponseRequestOtp()
//                val handleMessage = Json.encodeToJsonElement(it.response()?.errorBody()?.string())
//                handleMessage.message = it.response()?.errorBody()?.string().
                _message.postValue(it.response()?.errorBody()?.string())
//                _message.postValue(it.response()?.errorBody()?.string())
//                _message.postValue(it.message)
            }

            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun verifyOtp(phone_number: String, kode: String) {
        _loading.postValue(true)
        repositoryUser.repoVerifyOtp(phone_number, kode, {
            _loading.postValue(false)
            Log.d("status verify code",(it.code).toString())
            Log.d("status verify message",it.message ?: "no msg")
            _verify_otp.postValue(it)
        }, {

            if (it is HttpException) {
//                _message.postValue(it.message())
                _message.postValue(it.response()?.errorBody()?.string())
            }

            _loading.postValue(false)
            _error.postValue(it)
        })
    }

}