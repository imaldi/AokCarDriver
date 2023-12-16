package com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseDelNotifikasi(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
