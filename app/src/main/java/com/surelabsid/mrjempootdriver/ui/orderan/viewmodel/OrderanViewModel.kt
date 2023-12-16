package com.surelabsid.mrjempootdriver.ui.orderan.viewmodel

import android.app.Application
import android.util.Log
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
import com.surelabsid.mrjempootdriver.ui.orderan.modelrequest.*
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.*
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderanViewModel @Inject constructor(
    val repositoryTransaction: RepositoryTransaction,
    val repositoryLocalOrderan: RepositoryLocalOrderan,
    val application: Application
) : ViewModel() {

    private val _service = MutableLiveData<ResponseService>()
    val service: LiveData<ResponseService> = _service

    private val _status_transaction = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction: LiveData<ResponseStatusTransaction> = _status_transaction

    private val _status_transaction_gopek_terima = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_gopek_terima: LiveData<ResponseStatusTransaction> = _status_transaction_gopek_terima

    private val _status_transaction_tolak_orderan = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_tolak_orderan: LiveData<ResponseStatusTransaction> =
        _status_transaction_tolak_orderan

    private val _status_transaction_jemput = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_jemput: LiveData<ResponseStatusTransaction> = _status_transaction_jemput

    private val _status_transaction_jemput_paket = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_jemput_paket: LiveData<ResponseStatusTransaction> = _status_transaction_jemput_paket

    private val _status_transaction_jemput_lagi = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_jemput_lagi: LiveData<ResponseStatusTransaction> = _status_transaction_jemput_lagi

    private val _status_transaction_ambil_paket = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_ambil_paket: LiveData<ResponseStatusTransaction> = _status_transaction_ambil_paket

    private val _status_transaction_antar_paket = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_antar_paket: LiveData<ResponseStatusTransaction> = _status_transaction_antar_paket

    private val _status_transaction_paket_sampai = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_paket_sampai: LiveData<ResponseStatusTransaction> = _status_transaction_paket_sampai

    private val _status_transaction_selesai_antar_paket = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_selesai_antar_paket: LiveData<ResponseStatusTransaction> = _status_transaction_selesai_antar_paket

    private val _status_transaction_ambil_foto_finger = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_ambil_foto_finger: LiveData<ResponseStatusTransaction> = _status_transaction_ambil_foto_finger

    private val _status_transaction_tagih_customer_paket = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_tagih_customer_paket: LiveData<ResponseStatusTransaction> = _status_transaction_tagih_customer_paket

    private val _status_transaction_sudah_di_restoran = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_sudah_di_restoran: LiveData<ResponseStatusTransaction> = _status_transaction_sudah_di_restoran

    private val _status_transaction_sudah_di_pesan = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_sudah_di_pesan: LiveData<ResponseStatusTransaction> = _status_transaction_sudah_di_pesan

    private val _status_transaction_bayar_merchant = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_bayar_merchant: LiveData<ResponseStatusTransaction> = _status_transaction_bayar_merchant

    private val _status_transaction_kirim_struk_customer = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_kirim_struk_customer: LiveData<ResponseStatusTransaction> = _status_transaction_kirim_struk_customer

    private val _status_transaction_pesanan_sedang_diantar = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_pesanan_sedang_diantar: LiveData<ResponseStatusTransaction> = _status_transaction_pesanan_sedang_diantar

    private val _status_transaction_pesanan_sudah_diantar = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_pesanan_sudah_diantar: LiveData<ResponseStatusTransaction> = _status_transaction_pesanan_sudah_diantar

    private val _status_transaction_sama_customer = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_sama_customer: LiveData<ResponseStatusTransaction> =
        _status_transaction_sama_customer

    private val _status_transaction_tujuan = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_tujuan: LiveData<ResponseStatusTransaction> =
        _status_transaction_tujuan

    private val _status_transaction_selesai = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_selesai: LiveData<ResponseStatusTransaction> =
        _status_transaction_selesai

    private val _status_transaction_bayar = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_bayar: LiveData<ResponseStatusTransaction> =
        _status_transaction_bayar

    private val _status_transaction_tagih = MutableLiveData<ResponseStatusTransaction>()
    val status_transaction_tagih: LiveData<ResponseStatusTransaction> =
        _status_transaction_tagih

    private val _transaction = MutableLiveData<ResponseTransactionByService>()
    val transaction: LiveData<ResponseTransactionByService> = _transaction

    private val _nilai_customer = MutableLiveData<ResponseNilaiCustomer>()
    val nilai_customer: LiveData<ResponseNilaiCustomer> = _nilai_customer

    private val _bayar_mr_jempoot = MutableLiveData<ResponseBayar>()
    val bayar_mr_jempoot: LiveData<ResponseBayar> = _bayar_mr_jempoot

    private val _bayar_merchant = MutableLiveData<ResponseBayar>()
    val bayar_merchant: LiveData<ResponseBayar> = _bayar_merchant

    private val _verify_code_merchant = MutableLiveData<ResponseStatusTransaction>()
    val verify_code_merchant: LiveData<ResponseStatusTransaction> = _verify_code_merchant

    private val _send_struk = MutableLiveData<ResponseStatusTransaction>()
    val send_struk: LiveData<ResponseStatusTransaction> = _send_struk

    private val _send_bukti_terima_paket = MutableLiveData<ResponseStatusTransaction>()
    val send_bukti_terima_paket: LiveData<ResponseStatusTransaction> = _send_bukti_terima_paket

    private val _send_bukti_foto_jemput_paket = MutableLiveData<ResponseStatusTransaction>()
    val send_bukti_foto_jemput_paket: LiveData<ResponseStatusTransaction> = _send_bukti_foto_jemput_paket

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _loading_tolak_orderan = MutableLiveData<Boolean>()
    val loading_tolak_orderan: LiveData<Boolean> = _loading_tolak_orderan

    private val _loading_button_process = MutableLiveData<Boolean>()
    val loading_button_process: LiveData<Boolean> = _loading_button_process

    private val _loading_2 = MutableLiveData<Boolean>()
    val loading_2: LiveData<Boolean> = _loading_2

    private val _is_connected = MutableLiveData<Boolean>()
    val is_connected: LiveData<Boolean> = _is_connected

    private val _add_orderan_local = MutableLiveData<Boolean>()
    val add_orderan_local: LiveData<Boolean> = _add_orderan_local

    private val _list_orderan_by_id = MutableLiveData<List<EntityOrderan>>()
    val list_orderan_by_id: LiveData<List<EntityOrderan>> = _list_orderan_by_id

    private val _list_orderan_local = MutableLiveData<List<EntityOrderan>>()
    val list_orderan_local: LiveData<List<EntityOrderan>> = _list_orderan_local

    private val _update_button_name_orderan_local = MutableLiveData<Boolean>()
    val update_button_name_orderan_local: LiveData<Boolean> = _update_button_name_orderan_local

    fun getService() {
        _loading.postValue(true)
        if (NetworkUtils.isConnected(application.applicationContext)) {
            _is_connected.postValue(true)
            repositoryTransaction.repoGetService({
                _loading.postValue(false)
                _service.postValue(it)
            }, {
                _loading.postValue(false)
                _error.postValue(it)
            })

        } else {
            _loading.postValue(false)
            _is_connected.postValue(false)
        }
    }

    fun transactionByService(driver_id: String, service_id: Int) {
        _loading.postValue(true)
        if (NetworkUtils.isConnected(application.applicationContext)) {
            _is_connected.postValue(true)
            repositoryTransaction.repoTransactionByService(driver_id, service_id, {
                _loading.postValue(false)
                _transaction.postValue(it)
            }, {
                _loading.postValue(false)
                _error.postValue(it)
            })

        } else {
            _loading.postValue(false)
            _is_connected.postValue(false)
        }
    }

    fun transactionByServiceByStatus(driver_id: String, service_id: Int, transaction_status: Int) {
        _loading_2.postValue(true)
        Log.d("TR Driver ID", "$driver_id")
        Log.d("TR Service ID", "$service_id")
        Log.d("TR Status ID", "$transaction_status")
        if (NetworkUtils.isConnected(application.applicationContext)) {
            _is_connected.postValue(true)
            repositoryTransaction.repoTransactionByServiceByStatus(
                driver_id,
                service_id,
                transaction_status,
                {
                    _loading_2.postValue(false)
                    _transaction.postValue(it)
                },
                {
                    _loading_2.postValue(false)
                    _error.postValue(it)
                })

        } else {
            _loading_2.postValue(false)
            _is_connected.postValue(false)
        }
    }

    fun transactionStatus(param: RequestStatusTransaction) {
        _loading.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading.postValue(false)
                _status_transaction.postValue(it)
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionTolakOrderan(param: RequestStatusTransaction) {
        _loading_tolak_orderan.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_tolak_orderan.postValue(false)
                _status_transaction_tolak_orderan.postValue(it)
            },
            {
                _loading_tolak_orderan.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusJemput(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_jemput.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusJemputPaket(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_jemput_paket.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusAmbilPaket(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_ambil_paket.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusAntarPaket(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_antar_paket.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusPaketSampai(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_paket_sampai.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusSelesaiAntarPaket(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_selesai_antar_paket.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusAmbilFotoFinger(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_ambil_foto_finger.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusTagihCustomerPaket(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_tagih_customer_paket.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusJemputLagi(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_jemput_lagi.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusSudahDiRestoran(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_sudah_di_restoran.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusSudahDiPesan(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_sudah_di_pesan.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusBayarMerchant(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_bayar_merchant.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusKirimStrukCustomer(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_kirim_struk_customer.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusPesananSedangDiantar(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_pesanan_sedang_diantar.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusPesananSudahDiantar(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_pesanan_sudah_diantar.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusSamaCustomer(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_sama_customer.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusTujuan(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_tujuan.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusSelesai(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_selesai.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusBayar(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_bayar.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun transactionStatusTagih(param: RequestStatusTransaction) {
        _loading_button_process.postValue(true)
        repositoryTransaction.repoStatusTransaction(
            param,
            {
                _loading_button_process.postValue(false)
                _status_transaction_tagih.postValue(it)
            },
            {
                _loading_button_process.postValue(false)
                _error.postValue(it)
            })

    }

    fun nilaiCustomer(param: RequestNilaiCustomer) {
        _loading.postValue(true)
        repositoryTransaction.repoNilaiCustomer(
            param,
            {
                _loading.postValue(false)
                _nilai_customer.postValue(it)
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })
    }

    fun bayarKeAokCar(param: RequestBayarKeAokCar) {
        _loading.postValue(true)
        repositoryTransaction.repoBayarKeAokCar(
            param,
            {
                _loading.postValue(false)
                Log.d("Payment Type","Response Bayar Wallet Payment: ${it.data?.first()?.walletPayment ?: "Null datanya"}")
                _bayar_mr_jempoot.postValue(it)
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })
    }
    fun bayarKeMerchant(param: RequestBayarKeMerchant) {
        _loading.postValue(true)
        repositoryTransaction.repoBayarKeMerchant(
            param,
            {
                _loading.postValue(false)
                _bayar_merchant.postValue(it)
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })
    }
    fun verifyCodeMerchant(param: RequestVerifyCodeMerchant) {
        _loading.postValue(true)
        repositoryTransaction.repoVerifyCodeMerchant(
            param,
            {
                _loading.postValue(false)
                _verify_code_merchant.postValue(it)
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })
    }
    fun sendStruk(param: RequestSendStruk) {
        _loading.postValue(true)
        repositoryTransaction.repoSendStruk(
            param,
            {
                _loading.postValue(false)
                _send_struk.postValue(it)
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })
    }
    fun sendBuktiTerimaPaket(param: RequestSendBuktiTerimaPaket) {
        _loading.postValue(true)
        repositoryTransaction.repoSendBuktiTerimaPaket(
            param,
            {
                _loading.postValue(false)
                _send_bukti_terima_paket.postValue(it)
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })
    }
    fun sendBuktiFotoJemputPaket(param: RequestSendBuktiTerimaPaket) {
        _loading.postValue(true)
        repositoryTransaction.repoSendBuktiTerimaPaket(
            param,
            {
                _loading.postValue(false)
                _send_bukti_foto_jemput_paket.postValue(it)
            },
            {
                _loading.postValue(false)
                _error.postValue(it)
            })
    }

    fun addOrderantoLocal(entityOrderan: EntityOrderan) {
        repositoryLocalOrderan.insertOrderan(entityOrderan, {
            if (it) {
                _add_orderan_local.value = true
            }
        }, {
            _error.value = it
        })
    }

    fun getOrderanByid(id_orderan: Int) {
        repositoryLocalOrderan.getOrderanById(id_orderan, {
            _list_orderan_by_id.value = it
        }, {
            _error.value = it
        })
    }

    fun getOrderanLocal() {
        repositoryLocalOrderan.getOrderan({
            _list_orderan_local.value = it
        }, {
            _error.value = it
        })
    }

    fun updateButtonNameOrderanLocal(id_orderan: Int, button_name: String) {
        repositoryLocalOrderan.updateButtonName(id_orderan, button_name, {
            if (it) {
                _update_button_name_orderan_local.value = true
            }
        }, {
            _error.value = it
        })
    }


    fun onClear() {
        repositoryTransaction.onClear()
    }
}