package com.surelabsid.mrjempootdriver.ui.notifikasi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryNotifikasi
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.repository.RepositoryWallet
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.ResponseHome
import com.surelabsid.mrjempootdriver.ui.ewallet.modelrequest.RequestWithdraw
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseHistoryWallet
import com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse.ResponseWithdraw
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseDelNotifikasi
import com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse.ResponseNotifikasi
import com.surelabsid.mrjempootdriver.ui.topup.modelrequest.RequestReqTopup
import com.surelabsid.mrjempootdriver.ui.topup.modelresponse.ResponseReqTopup
import com.surelabsid.mrjempootdriver.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotifikasiViewModel @Inject constructor(
    val repositoryNotifikasi: RepositoryNotifikasi,
    val application: Application
) : ViewModel() {

    private val _list_notifikasi = MutableLiveData<ResponseNotifikasi>()
    val list_notifikasi: LiveData<ResponseNotifikasi> = _list_notifikasi

    private val _del_notifikasi = MutableLiveData<ResponseDelNotifikasi>()
    val del_notifikasi: LiveData<ResponseDelNotifikasi> = _del_notifikasi

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _is_connected = MutableLiveData<Boolean>()
    val is_connected: LiveData<Boolean> = _is_connected

    fun listNotifikasi(driver_id: String?) {
        _loading.postValue(true)
        repositoryNotifikasi.repoGetNotif(driver_id ?: "", {
            if (it.code == "200") {
                _loading.postValue(false)
                _list_notifikasi.postValue(it)
            }
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun delNotifikasi(driver_id: String?) {
        _loading.postValue(true)
        repositoryNotifikasi.repoDelNotif(driver_id ?: "", {
            if (it.code == "200") {
                _loading.postValue(false)
                _del_notifikasi.postValue(it)
            }
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })
    }

    fun onClear() {
        repositoryNotifikasi.onClear()
    }




}