package com.surelabsid.mrjempootdriver.ui.butuhbantuan.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseSendChatCS(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("detail_chat")
	val detailChat: List<DetailChatItem?>? = null,

	@field:SerializedName("url_img_merchant")
	val urlImgMerchant: String? = null,

	@field:SerializedName("date_created")
	val dateCreated: String? = null,

	@field:SerializedName("partner_name")
	val partnerName: Any? = null,

	@field:SerializedName("url_img_driver")
	val urlImgDriver: String? = null,

	@field:SerializedName("url_img_customer")
	val urlImgCustomer: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("url_img_chat")
	val urlImgChat: String? = null,

	@field:SerializedName("customer_fullname")
	val customerFullname: Any? = null
)

data class DetailChatItem(

	@field:SerializedName("id_message")
	val idMessage: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
