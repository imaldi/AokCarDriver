package com.surelabsid.mrjempootdriver.ui.pendaftaran.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseStatusPendaftaran(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("why_rejected")
	val whyRejected: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
