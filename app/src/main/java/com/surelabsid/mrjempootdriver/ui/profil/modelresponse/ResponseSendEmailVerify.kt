package com.surelabsid.mrjempootdriver.ui.profil.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseSendEmailVerify(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: SendEmailVerify? = null
)

data class SendEmailVerify(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
