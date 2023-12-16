package com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseRelationship(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataItemRelationShip?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemRelationShip(

	@field:SerializedName("date_at")
	val dateAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("relationship")
	val relationship: String? = null
)
