package com.surelabsid.mrjempootdriver.ui.ulasan.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponsePerformanceDriver(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DetailItem(

	@field:SerializedName("transaction_id")
	val transactionId: String? = null,

	@field:SerializedName("number")
	val number: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("update_at")
	val updateAt: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("customer_fullname")
	val customerFullname: Any? = null
)

data class CountRate(

	@field:SerializedName("rate_4")
	val rate4: Int? = null,

	@field:SerializedName("rate_3")
	val rate3: Int? = null,

	@field:SerializedName("rate_5")
	val rate5: Int? = null,

	@field:SerializedName("rate_2")
	val rate2: Int? = null,

	@field:SerializedName("rate_1")
	val rate1: Int? = null
)

data class Rating(

	@field:SerializedName("rating")
	val rating: String? = null
)

data class Data(

	@field:SerializedName("rating")
	val rating: Rating? = null,

	@field:SerializedName("detail")
	val detail: List<DetailItem?>? = null,

	@field:SerializedName("count_rate")
	val countRate: CountRate? = null
)
