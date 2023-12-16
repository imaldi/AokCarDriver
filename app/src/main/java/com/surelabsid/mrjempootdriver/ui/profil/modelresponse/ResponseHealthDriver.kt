package com.surelabsid.mrjempootdriver.ui.profil.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseHealthDriver(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: DataItemKesehatan? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemKesehatan(

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("vaccine")
	val vaccine: String? = null,

	@field:SerializedName("swab")
	val swab: String? = null,

	@field:SerializedName("date_healty")
	val dateHealty: String? = null,

	@field:SerializedName("id_health")
	val idHealth: String? = null,

	@field:SerializedName("free_covid")
	val freeCovid: String? = null
)
