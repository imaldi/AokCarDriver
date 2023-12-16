package com.surelabsid.mrjempootdriver.ui.callandchat.model

data class SendChat (val id_user: String? = null, val message: String? = null, val photo: String? = null, var timestamp: Long = System.currentTimeMillis())