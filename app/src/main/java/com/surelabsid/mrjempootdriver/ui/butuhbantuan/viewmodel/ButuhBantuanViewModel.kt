package com.surelabsid.mrjempootdriver.ui.butuhbantuan.viewmodel

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
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelrequest.RequestSendChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelrequest.RequestStartChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseGetChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseSendChatCS
import com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse.ResponseStartChatCS
import com.surelabsid.mrjempootdriver.ui.ewallet.modelrequest.RequestWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseWithdraw
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestRedeemPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponsePoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponseSouvenir
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ButuhBantuanViewModel @Inject constructor(
    val repositoryUser: RepositoryUser,
    val application: Application
) : ViewModel() {

    private val _start_chat_cs = MutableLiveData<ResponseStartChatCS>()
    val start_chat_cs: LiveData<ResponseStartChatCS> = _start_chat_cs

    private val _send_chat_cs = MutableLiveData<ResponseSendChatCS>()
    val send_chat_cs: LiveData<ResponseSendChatCS> = _send_chat_cs

    private val _get_chat_cs = MutableLiveData<ResponseGetChatCS>()
    val get_chat_cs: LiveData<ResponseGetChatCS> = _get_chat_cs

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _loading_send_chat = MutableLiveData<Boolean>()
    val loading_send_chat: LiveData<Boolean> = _loading_send_chat

    private val _is_connected = MutableLiveData<Boolean>()
    val is_connected: LiveData<Boolean> = _is_connected

    fun startChatCS(param: RequestStartChatCS) {
        _loading.postValue(true)
        repositoryUser.repoStartChatCS(param, {
            _loading.postValue(false)
            _start_chat_cs.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun sendChatCS(param: RequestSendChatCS) {
        _loading_send_chat.postValue(true)
        repositoryUser.repoSendChatCS(param, {
            _loading_send_chat.postValue(false)
            _send_chat_cs.postValue(it)
        }, {
            _loading_send_chat.postValue(false)
            _error.postValue(it)
        })
    }

    fun getChatCS(id_message: String) {
        _loading.postValue(true)
        repositoryUser.repoGetChatCS(id_message, {
            _loading.postValue(false)
            _get_chat_cs.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

}