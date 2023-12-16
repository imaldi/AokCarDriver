package com.surelabsid.mrjempootdriver.ui.orderan.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseService(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataItemService?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemService(

	@field:SerializedName("cost_desc")
	val costDesc: String? = null,

	@field:SerializedName("cost")
	val cost: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("commision")
	val commision: String? = null,

	@field:SerializedName("minimum_cost")
	val minimumCost: String? = null,

	@field:SerializedName("minimum_distance")
	val minimumDistance: String? = null,

	@field:SerializedName("minimum_wallet")
	val minimumWallet: String? = null,

	@field:SerializedName("home")
	val home: String? = null,

	@field:SerializedName("service")
	val service: String? = null,

	@field:SerializedName("service_id")
	val serviceId: String? = null,

	@field:SerializedName("driver_job")
	val driverJob: String? = null,

	@field:SerializedName("maks_distance")
	val maksDistance: String? = null,

	@field:SerializedName("voucher_discount")
	val voucherDiscount: String? = null
)
