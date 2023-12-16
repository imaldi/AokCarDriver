package com.surelabsid.mrjempootdriver.ui.orderan.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseBayar(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItemBayar>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemBayar(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("merchant_latitude")
	val merchantLatitude: Any? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("end_longitude")
	val endLongitude: String? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("pickup_address")
	val pickupAddress: String? = null,

	@field:SerializedName("sender_name")
	val senderName: Any? = null,

	@field:SerializedName("order_time")
	val orderTime: String? = null,

	@field:SerializedName("commision")
	val commision: String? = null,

	@field:SerializedName("poin_commision")
	val poinCommision: String? = null,

	@field:SerializedName("minimum_wallet")
	val minimumWallet: String? = null,

	@field:SerializedName("number")
	val number: Any? = null,

	@field:SerializedName("wallet_payment")
	val walletPayment: String? = null,

	@field:SerializedName("trip")
	val trip: String? = null,

	@field:SerializedName("telepon_pelanggan")
	val teleponPelanggan: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("open_hour")
	val openHour: Any? = null,

	@field:SerializedName("maks_distance")
	val maksDistance: String? = null,

	@field:SerializedName("merchant_open_status")
	val merchantOpenStatus: Any? = null,

	@field:SerializedName("transaction_id")
	val transactionId: Any? = null,

	@field:SerializedName("cost_desc")
	val costDesc: String? = null,

	@field:SerializedName("merchant_phone_number")
	val merchantPhoneNumber: Any? = null,

	@field:SerializedName("item_transaction")
	val itemTransaction: List<Any?>? = null,

	@field:SerializedName("merchant_name")
	val merchantName: Any? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("merchant_status")
	val merchantStatus: Any? = null,

	@field:SerializedName("merchant_desc")
	val merchantDesc: Any? = null,

	@field:SerializedName("finish_time")
	val finishTime: Any? = null,

	@field:SerializedName("goods_item")
	val goodsItem: Any? = null,

	@field:SerializedName("minimum_distance")
	val minimumDistance: String? = null,

	@field:SerializedName("send_id")
	val sendId: Any? = null,

	@field:SerializedName("merchant_country_code")
	val merchantCountryCode: Any? = null,

	@field:SerializedName("merchant_image")
	val merchantImage: Any? = null,

	@field:SerializedName("update_at")
	val updateAt: Any? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("promo_discount")
	val promoDiscount: String? = null,

	@field:SerializedName("note_rating_driver")
	val noteRatingDriver: Any? = null,

	@field:SerializedName("customer_image")
	val customerImage: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("note")
	val note: Any? = null,

	@field:SerializedName("sender_phone")
	val senderPhone: Any? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("transaction_status")
	val transactionStatus: String? = null,

	@field:SerializedName("distance")
	val distance: String? = null,

	@field:SerializedName("email_pelanggan")
	val emailPelanggan: String? = null,

	@field:SerializedName("merchant_logo")
	val merchantLogo: Any? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("service_order")
	val serviceOrder: String? = null,

	@field:SerializedName("merchant_id")
	val merchantId: Any? = null,

	@field:SerializedName("merchant_telephone_number")
	val merchantTelephoneNumber: Any? = null,

	@field:SerializedName("end_latitude")
	val endLatitude: String? = null,

	@field:SerializedName("merchant_category")
	val merchantCategory: Any? = null,

	@field:SerializedName("minimum_cost")
	val minimumCost: String? = null,

	@field:SerializedName("customer_fullname")
	val customerFullname: String? = null,

	@field:SerializedName("close_hour")
	val closeHour: Any? = null,

	@field:SerializedName("estimate_time")
	val estimateTime: String? = null,

	@field:SerializedName("rate")
	val rate: String? = null,

	@field:SerializedName("total_belanja")
	val totalBelanja: Any? = null,

	@field:SerializedName("start_latitude")
	val startLatitude: String? = null,

	@field:SerializedName("service_id")
	val serviceId: String? = null,

	@field:SerializedName("driver_job")
	val driverJob: String? = null,

	@field:SerializedName("receiver_name")
	val receiverName: Any? = null,

	@field:SerializedName("merchant_address")
	val merchantAddress: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("cost")
	val cost: String? = null,

	@field:SerializedName("destination_address")
	val destinationAddress: String? = null,

	@field:SerializedName("keterangan_history_trx")
	val keteranganHistoryTrx: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("reg_id")
	val regId: String? = null,

	@field:SerializedName("merchant_longitude")
	val merchantLongitude: Any? = null,

	@field:SerializedName("home")
	val home: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("start_longitude")
	val startLongitude: String? = null,

	@field:SerializedName("final_cost")
	val finalCost: String? = null,

	@field:SerializedName("service")
	val service: String? = null,

	@field:SerializedName("merchant_token")
	val merchantToken: Any? = null,

	@field:SerializedName("receiver_phone")
	val receiverPhone: Any? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null
)

