package com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseListBank(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("name_bank")
	val nameBank: String? = null,

	@field:SerializedName("date_at")
	val dateAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
