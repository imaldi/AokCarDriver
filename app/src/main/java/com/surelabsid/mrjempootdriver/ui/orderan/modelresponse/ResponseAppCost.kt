package com.surelabsid.mrjempootdriver.ui.orderan.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseAppCost(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("app_currency_text")
	val appCurrencyText: String? = null,

	@field:SerializedName("app_currency")
	val appCurrency: String? = null,

	@field:SerializedName("app_cost")
	val appCost: String? = null
)
