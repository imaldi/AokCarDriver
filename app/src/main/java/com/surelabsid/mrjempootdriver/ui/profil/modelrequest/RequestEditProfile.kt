package com.surelabsid.mrjempootdriver.ui.profil.modelrequest

data class RequestEditProfile(
    val customer_fullname: String = "",
    val dob: String = "",
    val fotodriver_lama: String? = null,
    val fotodriver: String? = null,
    val phone_number: String = "",
    val gender: String = "",
    val driver_address: String = "",
)