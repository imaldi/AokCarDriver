package com.surelabsid.mrjempootdriver.ui.orderan.modelrequest

data class RequestSendBuktiTerimaPaket(
    val driver_id: String,
    val transaction_id: String,
    val before: String,
    val after: String
)