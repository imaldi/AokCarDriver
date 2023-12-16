package com.surelabsid.mrjempootdriver.ui.profil.modelresponse

import com.google.gson.annotations.SerializedName

data class ResponseEditProfile(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("driver_address")
	val driverAddress: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("countrycode")
	val countrycode: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("driver_birthplace")
	val driverBirthplace: Any? = null,

	@field:SerializedName("vehicle")
	val vehicle: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("balance")
	val balance: String? = null,

	@field:SerializedName("vehicle_registration_number")
	val vehicleRegistrationNumber: String? = null,

	@field:SerializedName("variant")
	val variant: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("vehicle_id")
	val vehicleId: String? = null,

	@field:SerializedName("brand")
	val brand: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("reg_id")
	val regId: String? = null,

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("dob")
	val dob: String? = null,

	@field:SerializedName("update_at")
	val updateAt: String? = null,

	@field:SerializedName("user_nationid")
	val userNationid: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("job")
	val job: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
