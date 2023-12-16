package com.surelabsid.mrjempootdriver.ui.ewallet.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseHistoryWallet(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Totaldiscount(

	@field:SerializedName("discount")
	val discount: String? = null
)

data class Totalordermin(

	@field:SerializedName("total")
	val total: String? = null
)

data class RekbankItem(

	@field:SerializedName("nama_rek")
	val namaRek: String? = null,

	@field:SerializedName("buku_tab")
	val bukuTab: String? = null,

	@field:SerializedName("date_at")
	val dateAt: String? = null,

	@field:SerializedName("no_rek")
	val noRek: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("status_rek")
	val statusRek: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_bank")
	val idBank: String? = null,

	@field:SerializedName("name_bank")
	val nameBank: String? = null
)

data class Totalorderplus(

	@field:SerializedName("total")
	val total: String? = null
)

data class Totalwithdraw(

	@field:SerializedName("total")
	val total: String? = null
)

data class Data(

	@field:SerializedName("totalwithdraw")
	val totalwithdraw: Totalwithdraw? = null,

	@field:SerializedName("totalorderplus")
	val totalorderplus: Totalorderplus? = null,

	@field:SerializedName("totalordermin")
	val totalordermin: Totalordermin? = null,

	@field:SerializedName("wallet")
	val wallet: List<WalletItem?>? = null,

	@field:SerializedName("totaldiscount")
	val totaldiscount: Totaldiscount? = null,

	@field:SerializedName("balance")
	val balance: Balance? = null,

	@field:SerializedName("rekbank")
	val rekbank: List<RekbankItem?>? = null,

	@field:SerializedName("totaltopup")
	val totaltopup: Totaltopup? = null,

	@field:SerializedName("currency")
	val currency: String? = null
)

data class WalletItem(

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("bank")
	val bank: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("wallet_account")
	val walletAccount: String? = null,

	@field:SerializedName("wallet_amount")
	val walletAmount: String? = null,

	@field:SerializedName("holder_name")
	val holderName: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Totaltopup(

	@field:SerializedName("total")
	val total: String? = null
)

data class Balance(

	@field:SerializedName("total")
	val total: String? = null
)
