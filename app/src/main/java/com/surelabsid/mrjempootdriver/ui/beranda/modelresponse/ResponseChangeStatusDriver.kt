package com.surelabsid.mrjempootdriver.ui.beranda.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseChangeStatusDriver(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("bearing")
	val bearing: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("uang_belanja")
	val uangBelanja: String? = null,

	@field:SerializedName("update_at")
	val updateAt: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
