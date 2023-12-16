package com.surelabsid.mrjempootdriver.ui.topup.modelresponse

import com.google.gson.annotations.SerializedName
import com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse.DataItem

data class ResponseReqTopup(

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)