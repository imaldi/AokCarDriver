package com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseWithdraw(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
