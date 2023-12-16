package com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseSouvenir(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class SouvenirItem(

	@field:SerializedName("id_souvenir")
	val idSouvenir: String? = null,

	@field:SerializedName("status_souvenir")
	val statusSouvenir: String? = null,

	@field:SerializedName("poin_buy")
	val poinBuy: String? = null,

	@field:SerializedName("img_souvenir")
	val imgSouvenir: String? = null,

	@field:SerializedName("name_souvenir")
	val nameSouvenir: String? = null
)

data class Data(

	@field:SerializedName("souvenir")
	val souvenir: List<SouvenirItem?>? = null
)
