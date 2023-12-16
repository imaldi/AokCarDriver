package com.surelabsid.mrjempootdriver.ui.orderan.modelresponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseStatusTransaction(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataItemTransaction?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ItemTransactionItem(

	@field:SerializedName("item_note")
	val itemNote: String? = null,

	@field:SerializedName("total_cost")
	val totalCost: String? = null,

	@field:SerializedName("promo_price")
	val promoPrice: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("promo_status")
	val promoStatus: String? = null,

	@field:SerializedName("merchant_id")
	val merchantId: String? = null,

	@field:SerializedName("item_category")
	val itemCategory: String? = null,

	@field:SerializedName("item_cuisine")
	val itemCuisine: String? = null,

//	@field:SerializedName("extra")
//	val extra: String? = null,

//	@field:SerializedName("variant")
//	val variant: String? = null,

	@field:SerializedName("item_amount")
	val itemAmount: String? = null,

	@field:SerializedName("stock")
	val stock: String? = null,

	@field:SerializedName("transaction_item_id")
	val transactionItemId: String? = null,

	@field:SerializedName("disuka")
	val disuka: String? = null,

	@field:SerializedName("transaction_id")
	val transactionId: String? = null,

	@field:SerializedName("item_desc")
	val itemDesc: String? = null,

	@field:SerializedName("item_id")
	val itemId: String? = null,

	@field:SerializedName("level_trx")
	val levelTrx: String? = null,

	@field:SerializedName("item_price")
	val itemPrice: String? = null,

	@field:SerializedName("variant_trx")
	val varianTrx: String? = null,

	@field:SerializedName("item_name")
	val itemName: String? = null,

	@field:SerializedName("created_item")
	val createdItem: String? = null,

	@field:SerializedName("item_status")
	val itemStatus: String? = null,

	@field:SerializedName("lev_or_ext")
	val levOrExt: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("item_image")
	val itemImage: String? = null,

	@field:SerializedName("etalase_id")
	val etalaseId: String? = null
) : Serializable

data class DataItemTransaction(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("merchant_latitude")
	val merchantLatitude: String? = null,

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
	val openHour: String? = null,

	@field:SerializedName("maks_distance")
	val maksDistance: String? = null,

	@field:SerializedName("receive_packet")
	val receivePacket: ReceivePacket? = null,

	@field:SerializedName("merchant_open_status")
	val merchantOpenStatus: String? = null,

	@field:SerializedName("transaction_id")
	val transactionId: Any? = null,

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
	val goodsItem: Any? = null,

	@field:SerializedName("minimum_distance")
	val minimumDistance: String? = null,

	@field:SerializedName("send_id")
	val sendId: Any? = null,

	@field:SerializedName("kode_trx")
	val kodeTrx: Any? = null,

	@field:SerializedName("merchant_country_code")
	val merchantCountryCode: String? = null,

	@field:SerializedName("merchant_image")
	val merchantImage: String? = null,

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
	val keterangan: Any? = null,

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
	val receiverName: Any? = null,

	@field:SerializedName("merchant_address")
	val merchantAddress: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("is_recurring")
	val isRecurring: Any? = null,

	@field:SerializedName("cost")
	val cost: String? = null,

	@field:SerializedName("destination_address")
	val destinationAddress: String? = null,

	@field:SerializedName("keterangan_history_trx")
	val keteranganHistoryTrx: Any? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("reg_id")
	val regId: String? = null,

	@field:SerializedName("dropoff_time")
	val dropoffTime: Any? = null,

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

	@field:SerializedName("receiver_phone")
	val receiverPhone: Any? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null
)

data class ReceivePacket(

	@field:SerializedName("transaction_id")
	val transactionId: String? = null,

	@field:SerializedName("id_receive")
	val idReceive: String? = null,

	@field:SerializedName("date_receive")
	val dateReceive: String? = null,

	@field:SerializedName("image_before")
	val imageBefore: String? = null,

	@field:SerializedName("image_after")
	val imageAfter: String? = null
) : Serializable
