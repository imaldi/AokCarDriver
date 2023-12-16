package com.surelabsid.mrjempootdriver.ui.orderan.modelrequest

data class RequestVerifyCodeMerchant(
    val driver_id: String,
    val transaction_id: String,
    val code: String
)