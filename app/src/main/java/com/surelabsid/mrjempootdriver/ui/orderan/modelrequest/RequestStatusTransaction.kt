package com.surelabsid.mrjempootdriver.ui.orderan.modelrequest

data class RequestStatusTransaction(
    val driver_id: String,
    val transaction_id: String,
    val status: String,
    val keterangan: String
)