package com.surelabsid.mrjempootdriver.ui.beranda.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseHome(

	@field:SerializedName("stripe_active")
	val stripeActive: String? = null,

	@field:SerializedName("app_contact")
	val appContact: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("currency_text")
	val currencyText: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("stripe_publish")
	val stripePublish: String? = null,

	@field:SerializedName("paypal_key")
	val paypalKey: String? = null,

	@field:SerializedName("balance")
	val balance: String? = null,

	@field:SerializedName("app_email")
	val appEmail: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("paypal_active")
	val paypalActive: String? = null,

	@field:SerializedName("paypal_mode")
	val paypalMode: String? = null,

	@field:SerializedName("app_aboutus")
	val appAboutus: String? = null,

	@field:SerializedName("app_website")
	val appWebsite: String? = null
)
