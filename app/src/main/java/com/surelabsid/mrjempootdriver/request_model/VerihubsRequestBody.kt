package com.surelabsid.mrjempootdriver.request_model

import retrofit2.http.Query

data class VerihubsRequestBody (
    val msisdn: String,
    val otp: String,
)
