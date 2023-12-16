package com.surelabsid.mrjempootdriver.ui.topup.modelrequest

data class RequestWallet(
    var idUser: String? = null,
    var id: String? = null,
    var walletAmount: Double? = null,
    var type: String? = null,
    var date: String? = null,
    var status: Int? = null
)