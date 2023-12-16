package com.surelabsid.mrjempootdriver.ui.verifikasikesehatan.modelrequest

data class RequestHealthVerification(
    val driver_id: String,
    val vaccine: String,
    val swab: String,
    val free_covid: String
)