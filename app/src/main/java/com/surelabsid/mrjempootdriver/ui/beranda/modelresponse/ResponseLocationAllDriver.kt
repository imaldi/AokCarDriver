package com.surelabsid.mrjempootdriver.ui.beranda.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseLocationAllDriver(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
