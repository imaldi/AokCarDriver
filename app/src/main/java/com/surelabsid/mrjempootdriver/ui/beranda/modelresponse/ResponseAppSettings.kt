package com.surelabsid.mrjempootdriver.ui.beranda.modelresponse

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class ResponseAppSettings(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("app_settings")
	val appSettings: AppSettings? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Serializable
data class AppSettings(

	@field:SerializedName("stripe_active")
	val stripeActive: String? = null,

	@field:SerializedName("app_contact")
	val appContact: String? = null,

	@field:SerializedName("midtrans_mode")
	val midtransMode: String? = null,

	@field:SerializedName("smtp_from")
	val smtpFrom: String? = null,

	@field:SerializedName("smtp_host")
	val smtpHost: String? = null,

	@field:SerializedName("app_logo")
	val appLogo: String? = null,

	@field:SerializedName("stripe_published_key")
	val stripePublishedKey: String? = null,

	@field:SerializedName("midtrans_server_key")
	val midtransServerKey: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("app_linkgoogle")
	val appLinkgoogle: String? = null,

	@field:SerializedName("midtrans_client_key_sb")
	val midtransClientKeySb: String? = null,

	@field:SerializedName("midtrans_payout_apikey_sb")
	val midtransPayoutApikeySb: String? = null,

	@field:SerializedName("app_syarat")
	val appSyarat: String? = null,

	@field:SerializedName("app_privacy_policy")
	val appPrivacyPolicy: String? = null,

	@field:SerializedName("midtrans_payout_apikey")
	val midtransPayoutApikey: String? = null,

	@field:SerializedName("smtp_port")
	val smtpPort: String? = null,

	@field:SerializedName("verihubs_key")
	val verihubsKey: String? = null,

	@field:SerializedName("verihubs_key_sb")
	val verihubsKeySb: String? = null,

	@field:SerializedName("verihubs_mode")
	val verihubsMode: String? = null,

	@field:SerializedName("app_address")
	val appAddress: String? = null,

	@field:SerializedName("paypal_active")
	val paypalActive: String? = null,

	@field:SerializedName("midtrans_server_key_sb")
	val midtransServerKeySb: String? = null,

	@field:SerializedName("email_subject")
	val emailSubject: String? = null,

	@field:SerializedName("app_website")
	val appWebsite: String? = null,

	@field:SerializedName("app_cost")
	val appCost: String? = null,

	@field:SerializedName("midtrans_id")
	val midtransId: String? = null,

	@field:SerializedName("midtrans_payout_baseurl_sb")
	val midtransPayoutBaseurlSb: String? = null,

	@field:SerializedName("smtp_username")
	val smtpUsername: String? = null,

	@field:SerializedName("paypal_key")
	val paypalKey: String? = null,

	@field:SerializedName("smtp_password")
	val smtpPassword: String? = null,

	@field:SerializedName("verihubs_app_id")
	val verihubsAppId: String? = null,

	@field:SerializedName("verihubs_app_id_sb")
	val verihubsAppIdSb: String? = null,

	@field:SerializedName("email_text1")
	val emailText1: String? = null,

	@field:SerializedName("email_text2")
	val emailText2: String? = null,

	@field:SerializedName("email_text3")
	val emailText3: String? = null,

	@field:SerializedName("email_text4")
	val emailText4: String? = null,

	@field:SerializedName("paypal_mode")
	val paypalMode: String? = null,

	@field:SerializedName("midtrans_baseurl_sb")
	val midtransBaseurlSb: String? = null,

	@field:SerializedName("app_aboutus")
	val appAboutus: String? = null,

	@field:SerializedName("stripe_secret_key")
	val stripeSecretKey: String? = null,

	@field:SerializedName("app_currency")
	val appCurrency: String? = null,

	@field:SerializedName("midtrans_payout_mode")
	val midtransPayoutMode: String? = null,

	@field:SerializedName("midtrans_payout_baseurl")
	val midtransPayoutBaseurl: String? = null,

	@field:SerializedName("app_currency_text")
	val appCurrencyText: String? = null,

	@field:SerializedName("midtrans_client_key")
	val midtransClientKey: String? = null,

	@field:SerializedName("app_name")
	val appName: String? = null,

	@field:SerializedName("smtp_secure")
	val smtpSecure: String? = null,

	@field:SerializedName("stripe_status")
	val stripeStatus: String? = null,

	@field:SerializedName("email_subject_confirm")
	val emailSubjectConfirm: String? = null,

	@field:SerializedName("app_email")
	val appEmail: String? = null,

	@field:SerializedName("midtrans_payout_merchantkey_sb")
	val midtransPayoutMerchantkeySb: String? = null,

	@field:SerializedName("midtrans_baseurl")
	val midtransBaseurl: String? = null,

	@field:SerializedName("midtrans_payout_merchantkey")
	val midtransPayoutMerchantkey: String? = null,

	@field:SerializedName("app_description")
	val appDescription: String? = null
)
