package com.surelabsid.mrjempootdriver.ui.beranda.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surelabsid.mrjempootdriver.repository.RepositoryApp
import com.surelabsid.mrjempootdriver.repository.RepositoryTransaction
import com.surelabsid.mrjempootdriver.repository.RepositoryUser
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestChangeStatusDriver
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestHome
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestTurningOn
import com.surelabsid.mrjempootdriver.ui.beranda.modelrequest.RequestUpdateLocation
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.*
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.ResponseAppCost
import com.surelabsid.mrjempootdriver.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repositoryUser: RepositoryUser,
    val repositoryTransaction: RepositoryTransaction,
    val repositoryApp: RepositoryApp,
    val application: Application
) : ViewModel() {

    private val _home = MutableLiveData<ResponseHome>()
    val home: LiveData<ResponseHome> = _home

    private val _turning_on = MutableLiveData<ResponseTurningOn>()
    val turning_on: LiveData<ResponseTurningOn> = _turning_on

    private val _app_cost = MutableLiveData<ResponseAppCost>()
    val app_cost: LiveData<ResponseAppCost> = _app_cost

    private val _change_status_driver = MutableLiveData<ResponseChangeStatusDriver>()
    val change_status_driver: LiveData<ResponseChangeStatusDriver> = _change_status_driver

    private val _app_settings = MutableLiveData<ResponseAppSettings>()
    val app_settings: LiveData<ResponseAppSettings> = _app_settings

    private val _update_location = MutableLiveData<ResponseUpdateLocation>()
    val update_location: LiveData<ResponseUpdateLocation> = _update_location

    private val _location_all_driver = MutableLiveData<ResponseLocationAllDriver>()
    val location_all_driver: LiveData<ResponseLocationAllDriver> = _location_all_driver

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _is_connected = MutableLiveData<Boolean>()
    val is_connected: LiveData<Boolean> = _is_connected

    fun home(param: RequestHome?) {
        _loading.postValue(true)
        if (NetworkUtils.isConnected(application.applicationContext)) {
            _is_connected.postValue(true)
            repositoryUser.repoHome(param, {
                if (it.code == "200") {
                    _loading.postValue(false)
                    _home.postValue(it)
                }
            }, {
                _loading.postValue(false)
                _error.postValue(it)
            })

        } else {
            _loading.postValue(false)
            _is_connected.postValue(false)
        }
    }

    fun turningOn(param: RequestTurningOn?) {
        _loading.postValue(true)
        if (NetworkUtils.isConnected(application.applicationContext)) {
            _is_connected.postValue(true)
            repositoryUser.repoTurningOn(param, {
                _loading.postValue(false)
                _turning_on.postValue(it)
            }, {
                _loading.postValue(false)
                _error.postValue(it)
            })

        } else {
            _loading.postValue(false)
            _is_connected.postValue(false)
        }
    }

    fun changeStatusDriver(param: RequestChangeStatusDriver) {
        _loading.postValue(true)
        repositoryUser.repoChangeStatusDriver(param, {
            _loading.postValue(false)
            _change_status_driver.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })

    }

    fun appCost() {
        _loading.postValue(true)
        repositoryApp.repoAppCost({
            _loading.postValue(false)
            _app_cost.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })

    }

    fun appSettings() {
        _loading.postValue(true)
        repositoryApp.repoAppSettings({
            _loading.postValue(false)
            _app_settings.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })

    }

    fun updateLocation(param: RequestUpdateLocation) {
        _loading.postValue(true)
        repositoryUser.repoUpdateLocation(param, {
            _loading.postValue(false)
            _update_location.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })

    }

    fun locationAllDriver() {
        _loading.postValue(true)
        repositoryUser.repoLocationAllDriver({
            _loading.postValue(false)
            _location_all_driver.postValue(it)
        }, {
            _loading.postValue(false)
            _error.postValue(it)
        })

    }
}