package com.surelabsid.mrjempootdriver.ui.login.modelresponse

import com.google.gson.annotations.SerializedName

class ResponseRequestOtp {
    @SerializedName("segment_count")
    var segmentCount = 0

    @SerializedName("code")
    var code = 0

    @SerializedName("try_count")
    var tryCount = 0

    @SerializedName("session_id")
    var sessionId: String? = null

    @SerializedName("otp")
    var otp: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("msisdn")
    var msisdn: String? = null
}