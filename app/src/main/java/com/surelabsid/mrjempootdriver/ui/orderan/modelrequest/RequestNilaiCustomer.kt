package com.surelabsid.mrjempootdriver.ui.orderan.modelrequest

data class RequestNilaiCustomer(
    val transaction_id: String,
    val driver_id: String,
    val customer_id: String,
    val grade_value: String
)