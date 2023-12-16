package com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponsePoin(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: DataPoin? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class BalancePoin(

	@field:SerializedName("total")
	val total: String? = null
)

data class WalletPoinItem(

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("partner_name")
	val partnerName: Any? = null,

	@field:SerializedName("amount_poin")
	val amountPoin: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("keterangan_poin")
	val keteranganPoin: String? = null,

	@field:SerializedName("date_wallet_poin")
	val dateWalletPoin: String? = null,

	@field:SerializedName("id_wallet_poin")
	val idWalletPoin: String? = null
)

data class DataPoin(

	@field:SerializedName("balance_poin")
	val balancePoin: BalancePoin? = null,

	@field:SerializedName("wallet_poin")
	val walletPoin: List<WalletPoinItem?>? = null
)
