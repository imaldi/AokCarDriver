package com.surelabsid.mrjempootdriver.ui.orderan.modelresponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseTransactionByService(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("merchant_latitude")
	val merchantLatitude: String? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("end_longitude")
	val endLongitude: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("pickup_address")
	val pickupAddress: String? = null,

	@field:SerializedName("sender_name")
	val senderName: String? = null,

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
	val openHour: String? = null,

	@field:SerializedName("maks_distance")
	val maksDistance: String? = null,

	@field:SerializedName("receive_packet")
	val receivePacket: ReceivePacket? = null,

	@field:SerializedName("merchant_open_status")
	val merchantOpenStatus: String? = null,

	@field:SerializedName("transaction_id")
	val transactionId: String? = null,

	@field:SerializedName("cost_desc")
	val costDesc: String? = null,

	@field:SerializedName("merchant_phone_number")
	val merchantPhoneNumber: String? = null,

	@field:SerializedName("item_transaction")
	val itemTransaction: List<ItemTransactionItem?>? = null,

	@field:SerializedName("merchant_name")
	val merchantName: String? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("merchant_status")
	val merchantStatus: String? = null,

	@field:SerializedName("merchant_desc")
	val merchantDesc: String? = null,

	@field:SerializedName("finish_time")
	val finishTime: String? = null,

	@field:SerializedName("goods_item")
	val goodsItem: String? = null,

	@field:SerializedName("minimum_distance")
	val minimumDistance: String? = null,

	@field:SerializedName("send_id")
	val sendId: String? = null,

	@field:SerializedName("kode_trx")
	val kodeTrx: String? = null,

	@field:SerializedName("id_langganan")
	val idLangganan: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("merchant_country_code")
	val merchantCountryCode: String? = null,

	@field:SerializedName("merchant_image")
	val merchantImage: String? = null,

	@field:SerializedName("update_at")
	val updateAt: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("promo_discount")
	val promoDiscount: String? = null,

	@field:SerializedName("note_rating_driver")
	val noteRatingDriver: String? = null,

	@field:SerializedName("customer_image")
	val customerImage: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("sender_phone")
	val senderPhone: String? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("transaction_status")
	val transactionStatus: String? = null,

	@field:SerializedName("distance")
	val distance: String? = null,

	@field:SerializedName("email_pelanggan")
	val emailPelanggan: String? = null,

	@field:SerializedName("merchant_logo")
	val merchantLogo: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("service_order")
	val serviceOrder: String? = null,

	@field:SerializedName("merchant_id")
	val merchantId: String? = null,

	@field:SerializedName("merchant_telephone_number")
	val merchantTelephoneNumber: String? = null,

	@field:SerializedName("end_latitude")
	val endLatitude: String? = null,

	@field:SerializedName("minimum_cost")
	val minimumCost: String? = null,

	@field:SerializedName("customer_fullname")
	val customerFullname: String? = null,

	@field:SerializedName("close_hour")
	val closeHour: String? = null,

	@field:SerializedName("estimate_time")
	val estimateTime: String? = null,

	@field:SerializedName("rate")
	val rate: String? = null,

	@field:SerializedName("total_belanja")
	val totalBelanja: String? = null,

	@field:SerializedName("start_latitude")
	val startLatitude: String? = null,

	@field:SerializedName("service_id")
	val serviceId: String? = null,

	@field:SerializedName("driver_job")
	val driverJob: String? = null,

	@field:SerializedName("receiver_name")
	val receiverName: String? = null,

	@field:SerializedName("merchant_address")
	val merchantAddress: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("is_recurring")
	val isRecurring: String? = null,

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

	@field:SerializedName("dropoff_time")
	val dropoffTime: String? = null,

	@field:SerializedName("merchant_longitude")
	val merchantLongitude: String? = null,

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
	val merchantToken: String? = null,

	@field:SerializedName("voucher_type")
	val voucherType: String? = null,

	@field:SerializedName("receiver_phone")
	val receiverPhone: String? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("poin_commision")
	val poinCommision: String? = null,

	@field:SerializedName("voucher_discount")
	val voucherDiscount: String? = null
): Serializable
