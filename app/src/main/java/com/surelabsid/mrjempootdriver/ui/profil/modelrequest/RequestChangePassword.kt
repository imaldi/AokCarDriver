package com.surelabsid.mrjempootdriver.ui.profil.modelrequest

data class RequestChangePassword(
    val phone_number: String = "",
    val password: String = "",
    val new_password: String = "",
)