package com.surelabsid.mrjempootdriver.ui.notifikasi.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseNotifikasi(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataItemNotifikasi?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemNotifikasi(

	@field:SerializedName("id_notif")
	val idNotif: String? = null,

	@field:SerializedName("date_notif")
	val dateNotif: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("text_notif")
	val textNotif: String? = null,

	@field:SerializedName("title_notif")
	val titleNotif: String? = null
)
