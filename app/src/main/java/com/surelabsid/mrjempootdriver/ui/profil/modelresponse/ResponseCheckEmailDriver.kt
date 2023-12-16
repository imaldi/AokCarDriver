package com.surelabsid.mrjempootdriver.ui.profil.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseCheckEmailDriver(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("is_verify")
	val isVerify: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
