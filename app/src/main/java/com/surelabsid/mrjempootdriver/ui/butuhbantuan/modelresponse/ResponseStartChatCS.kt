package com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseStartChatCS(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: DataItemMessage? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemMessage(

	@field:SerializedName("id_message")
	val idMessage: String? = null
)
