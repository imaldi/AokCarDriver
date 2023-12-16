package com.surelabsid.mrjempootdriver.ui.beranda.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseUpdateLocation(

	@field:SerializedName("data")
	val data: List<Any?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)
