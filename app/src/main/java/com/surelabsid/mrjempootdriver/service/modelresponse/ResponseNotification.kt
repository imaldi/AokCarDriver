package com.surelabsid.mrjempootdriver.service.modelresponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseNotification(

	@field:SerializedName("data")
	var data: MutableList<DataItem?>? = null,

	@field:SerializedName("data")
	var dataItem: DataItem? = null,

	@field:SerializedName("sound")
	val sound: String? = null,

	@field:SerializedName("body")
	var body: String? = null,

	@field:SerializedName("type")
	var type: String? = null,

	@field:SerializedName("title")
	var title: String? = null,

	var serviceId: String? = null
) : Serializable

data class DataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("id_langganan")
	val idLangganan: Any? = null,

	@field:SerializedName("merchant_latitude")
	val merchantLatitude: Any? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("end_longitude")
	val endLongitude: String? = null,

	@field:SerializedName("number_rating_driver")
	val numberRatingDriver: Any? = null,

	@field:SerializedName("pickup_address")
	val pickupAddress: String? = null,

	@field:SerializedName("sender_name")
	val senderName: String? = null,

	@field:SerializedName("status_merchant")
	val statusMerchant: Any? = null,

	@field:SerializedName("order_time")
	val orderTime: String? = null,

	@field:SerializedName("commision")
	val commision: String? = null,

	@field:SerializedName("minimum_wallet")
	val minimumWallet: String? = null,

	@field:SerializedName("number")
	val number: String? = null,

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

	@field:SerializedName("receive_packet")
	val receivePacket: Any? = null,

	@field:SerializedName("merchant_open_status")
	val merchantOpenStatus: Any? = null,

	@field:SerializedName("transaction_id")
	val transactionId: String? = null,

	@field:SerializedName("cost_desc")
	val costDesc: String? = null,

	@field:SerializedName("merchant_phone_number")
	val merchantPhoneNumber: Any? = null,

	@field:SerializedName("is_member")
	val isMember: Any? = null,

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
	val goodsItem: String? = null,

	@field:SerializedName("minimum_distance")
	val minimumDistance: String? = null,

	@field:SerializedName("send_id")
	val sendId: String? = null,

	@field:SerializedName("kode_trx")
	val kodeTrx: String? = null,

	@field:SerializedName("merchant_country_code")
	val merchantCountryCode: Any? = null,

	@field:SerializedName("merchant_image")
	val merchantImage: Any? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("pickup_time")
	val pickupTime: Any? = null,

	@field:SerializedName("promo_discount")
	val promoDiscount: String? = null,

	@field:SerializedName("note_rating_driver")
	val noteRatingDriver: Any? = null,

	@field:SerializedName("customer_image")
	val customerImage: Any? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("end_date")
	val endDate: Any? = null,

	@field:SerializedName("note")
	val note: Any? = null,

	@field:SerializedName("sender_phone")
	val senderPhone: String? = null,

	@field:SerializedName("keterangan")
	val keterangan: Any? = null,

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

	@field:SerializedName("minimum_cost")
	val minimumCost: String? = null,

	@field:SerializedName("customer_fullname")
	val customerFullname: String? = null,

	@field:SerializedName("duration")
	val duration: Any? = null,

	@field:SerializedName("close_hour")
	val closeHour: Any? = null,

	@field:SerializedName("url_merchant")
	val urlMerchant: String? = null,

	@field:SerializedName("estimate_time")
	val estimateTime: String? = null,

	@field:SerializedName("rate")
	val rate: String? = null,

	@field:SerializedName("total_belanja")
	val totalBelanja: Any? = null,

	@field:SerializedName("start_latitude")
	val startLatitude: String? = null,

	@field:SerializedName("service_id")
	var serviceId: String? = null,

	@field:SerializedName("driver_job")
	val driverJob: String? = null,

	@field:SerializedName("receiver_name")
	val receiverName: String? = null,

	@field:SerializedName("merchant_address")
	val merchantAddress: Any? = null,

	@field:SerializedName("merchant_rating")
	val merchantRating: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("is_recurring")
	val isRecurring: Any? = null,

	@field:SerializedName("start_date")
	val startDate: Any? = null,

	@field:SerializedName("cost")
	val cost: String? = null,

	@field:SerializedName("destination_address")
	val destinationAddress: String? = null,

	@field:SerializedName("keterangan_history_trx")
	val keteranganHistoryTrx: Any? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("url_driver")
	val urlDriver: String? = null,

	@field:SerializedName("reg_id")
	val regId: String? = null,

	@field:SerializedName("dropoff_time")
	val dropoffTime: Any? = null,

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

	@field:SerializedName("rate_driver")
	val rateDriver: Any? = null,

	@field:SerializedName("merchant_token")
	val merchantToken: Any? = null,

	@field:SerializedName("receiver_phone")
	val receiverPhone: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null
) : Serializable
