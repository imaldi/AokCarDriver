package com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseRegisterDriver(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
