package com.surelabsid.mrjempootdriver.utils

import android.content.Context
import android.content.SharedPreferences
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.AppSettings

class SessionManager (private val context: Context) {

    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor

    private val PRIVATE_MODE = 0
    private val PREF_NAME = "AokCarDriverApp"

    companion object {
        const val IS_DEV = "is_dev"

        const val KEY_TOKEN = "token"
        const val KEY_LOGIN = "is_login"
        const val GPS = "gps"
        const val LAT_USER = "lat_user"
        const val LNG_USER = "lng_user"
        const val LAT_ADDRESS = "lat_address"
        const val LNG_ADDRESS = "lng_address"
        const val ON_BOARDING = "on_boarding"

        //user
        const val ID = "id"
        const val ID_MESSAGE_CS = "id_message_cs"
        const val FLAG_DIAL_CODE = "flag_dial_code"
        const val DIAL_CODE = "dial_code"
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val BIAYA_PLATFORM = "biaya_platform"
        const val FULL_NAME = "full_name"
        const val BALANCE = "balance"
        const val POIN = "poin"
        const val EMAIL = "email"
        const val ADDRESS = "address"
        const val ADDRESS_TEMP = "address_temp"
        const val ADDRESS_DETAIL = "address_detail"
        const val GENDER = "gender"
        const val BIRTH_DATE = "birth_date"
        const val PHOTO_PROFILE = "photo_profile"
        const val EMERGENCY_FLAG_DIAL_CODE = "emergency_flag_dial_code"
        const val EMERGENCY_DIAL_CODE = "emergency_dial_code"
        const val EMERGENCY_PHONE_NUMBER = "emergency_phone_number"
        const val EMERGENCY_NAME = "emergency_name"
        const val EMERGENCY_RELATION_ID = "emergency_relation_id"
        const val EMERGENCY_RELATION = "emergency_relation"
        const val KTP_DOCUMENT = "ktp_document"
        const val KTP_NAME = "ktp_name"
        const val KTP_ID = "ktp_id"
        const val KTP_PHOTO_SELFIE = "ktp_photo_selfie"
//        const val BANK_ID = "bank_id"
//        const val BANK_PHOTO = "bank_photo"
//        const val BANK_NAME = "bank_name"
//        const val BANK_CABANG = "bank_cabang"
//        const val BANK_REKENING = "bank_rekening"
//        const val BANK_ACCOUNT_NAME = "bank_account_name"
        const val VEHICLE_PLATE = "vehicle_plat"
        const val VEHICLE_COLOR = "vehicle_color"
        const val VEHICLE_BRAND = "vehicle_brand"
        const val VEHICLE_TYPE = "vehicle_type"
        const val VEHICLE_KIND = "vehicle_kind"
        const val SIM_DOCUMENT = "sim_document"
        const val SIM_DOCUMENT_ID = "sim_document_id"
        const val STNK_DOCUMENT = "stnk_document"
        const val SKCK_DOCUMENT = "skck_document"
        const val RATING = "rating"
        const val JOB = "job"
        const val STATUS = "status"
        const val STATUS_CONFIG_DRIVER = "status_config_driver"

        const val SERVICE_ID = "service_id"

        const val FLAG_UPDATE = "flag_update"
        const val FLAG_RESET_PASSWORD = "flag_reset_password"
        const val FLAG_CREATE_PASSWORD = "flag_create_password"

        const val CURRENT_BUTTON = "current_button"
        const val TEMP_TRANSACTION_ID = "temp_transaction_id"

        const val TEMP_TRIP = "temp_trip"

        const val APP_SETTINGS = "app_settings"

        //Verihubs
        const val APP_ID_VERIHUBS = "app_id_verihubs"
        const val API_KEY_VERIHUBS = "api_key_verihubs"

    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun createLoginSession(token: String){
        editor.apply{
            putString(KEY_TOKEN, token)
            putBoolean(KEY_LOGIN, true)
            commit()
        }

    }

    var is_dev: Boolean
        get() = pref.getBoolean(IS_DEV, true)
        set(value) {
            editor.apply {
                putBoolean(IS_DEV, value)
                commit()
            }
        }

    var gps: Boolean
        get() = pref.getBoolean(GPS, false)
        set(value) {
            editor.apply {
                putBoolean(GPS, value)
                commit()
            }
        }

    var lat_user: String
        get() = pref.getString(LAT_USER, "0.0") ?: "0.0"
        set(value) {
            editor.apply {
                putString(LAT_USER, value)
                commit()
            }
        }

    var lng_user: String
        get() = pref.getString(LNG_USER, "0.0") ?: "0.0"
        set(value) {
            editor.apply {
                putString(LNG_USER, value)
                commit()
            }
        }

    var lat_address: String
        get() = pref.getString(LAT_ADDRESS, lat_user) ?: ""
        set(value) {
            editor.apply {
                putString(LAT_ADDRESS, value)
                commit()
            }
        }

    var lng_address: String
        get() = pref.getString(LNG_ADDRESS, lng_user) ?: ""
        set(value) {
            editor.apply {
                putString(LNG_ADDRESS, value)
                commit()
            }
        }

    var on_boarding: Boolean
        get() = pref.getBoolean(ON_BOARDING, false)
        set(value) {
            editor.apply {
                putBoolean(ON_BOARDING, value)
                commit()
            }
        }

    var id: String
        get() = pref.getString(ID, "") ?: ""
        set(value) {
            editor.apply {
                putString(ID, value)
                commit()
            }
        }

    var id_message_cs: String
        get() = pref.getString(ID_MESSAGE_CS, "") ?: ""
        set(value) {
            editor.apply {
                putString(ID_MESSAGE_CS, value)
                commit()
            }
        }

    var flag_dial_code: Int
        get() = pref.getInt(FLAG_DIAL_CODE, R.drawable.indo_flag)
        set(value) {
            editor.apply {
                putInt(FLAG_DIAL_CODE, value)
                commit()
            }
        }

    var dial_code: String
        get() = pref.getString(DIAL_CODE, "+62") ?: "+62"
        set(value) {
            editor.apply {
                putString(DIAL_CODE, value)
                commit()
            }
        }

    var username: String
        get() = pref.getString(USERNAME, "") ?: ""
        set(value) {
            editor.apply {
                putString(USERNAME, value)
                commit()
            }
        }

    var password: String
        get() = pref.getString(PASSWORD, "123456") ?: ""
        set(value) {
            editor.apply {
                putString(PASSWORD, value)
                commit()
            }
        }

    var biaya_platform: String
        get() = pref.getString(BIAYA_PLATFORM, "3000") ?: ""
        set(value) {
            editor.apply {
                putString(BIAYA_PLATFORM, value)
                commit()
            }
        }

    var full_name: String
        get() = pref.getString(FULL_NAME, "") ?: ""
        set(value) {
            editor.apply {
                putString(FULL_NAME, value)
                commit()
            }
        }

    var email: String
        get() = pref.getString(EMAIL, "") ?: ""
        set(value) {
            editor.apply {
                putString(EMAIL, value)
                commit()
            }
        }

    var address: String
        get() = pref.getString(ADDRESS, "") ?: ""
        set(value) {
            editor.apply {
                putString(ADDRESS, value)
                commit()
            }
        }

    var address_temp: String
        get() = pref.getString(ADDRESS_TEMP, "") ?: ""
        set(value) {
            editor.apply {
                putString(ADDRESS_TEMP, value)
                commit()
            }
        }

    var address_detail: String
        get() = pref.getString(ADDRESS_DETAIL, "") ?: ""
        set(value) {
            editor.apply {
                putString(ADDRESS_DETAIL, value)
                commit()
            }
        }

    var gender: String
        get() = pref.getString(GENDER, "") ?: ""
        set(value) {
            editor.apply {
                putString(GENDER, value)
                commit()
            }
        }

    var birth_date: String
        get() = pref.getString(BIRTH_DATE, "") ?: ""
        set(value) {
            editor.apply {
                putString(BIRTH_DATE, value)
                commit()
            }
        }

    var photo_profile: String
        get() = pref.getString(PHOTO_PROFILE, "") ?: ""
        set(value) {
            editor.apply {
                putString(PHOTO_PROFILE, value)
                commit()
            }
        }

    var emergency_flag_dial_code: Int
        get() = pref.getInt(EMERGENCY_FLAG_DIAL_CODE, R.drawable.indo_flag)
        set(value) {
            editor.apply {
                putInt(EMERGENCY_FLAG_DIAL_CODE, value)
                commit()
            }
        }

    var emergency_dial_code: String
        get() = pref.getString(EMERGENCY_DIAL_CODE, "+62") ?: "+62"
        set(value) {
            editor.apply {
                putString(EMERGENCY_DIAL_CODE, value)
                commit()
            }
        }

    var emergency_phone_number: String
        get() = pref.getString(EMERGENCY_PHONE_NUMBER, "") ?: ""
        set(value) {
            editor.apply {
                putString(EMERGENCY_PHONE_NUMBER, value)
                commit()
            }
        }

    var emergency_name: String
        get() = pref.getString(EMERGENCY_NAME, "") ?: ""
        set(value) {
            editor.apply {
                putString(EMERGENCY_NAME, value)
                commit()
            }
        }

    var emergency_relation_id: String
        get() = pref.getString(EMERGENCY_RELATION_ID, "") ?: ""
        set(value) {
            editor.apply {
                putString(EMERGENCY_RELATION_ID, value)
                commit()
            }
        }

    var emergency_relation: String
        get() = pref.getString(EMERGENCY_RELATION, "") ?: ""
        set(value) {
            editor.apply {
                putString(EMERGENCY_RELATION, value)
                commit()
            }
        }

    var ktp_document: String
        get() = pref.getString(KTP_DOCUMENT, "") ?: ""
        set(value) {
            editor.apply {
                putString(KTP_DOCUMENT, value)
                commit()
            }
        }

    var ktp_name: String
        get() = pref.getString(KTP_NAME, "") ?: ""
        set(value) {
            editor.apply {
                putString(KTP_NAME, value)
                commit()
            }
        }

    var ktp_id: String
        get() = pref.getString(KTP_ID, "") ?: ""
        set(value) {
            editor.apply {
                putString(KTP_ID, value)
                commit()
            }
        }

    var ktp_photo_selfie: String
        get() = pref.getString(KTP_PHOTO_SELFIE, "") ?: ""
        set(value) {
            editor.apply {
                putString(KTP_PHOTO_SELFIE, value)
                commit()
            }
        }

//    var bank_id: String
//        get() = pref.getString(BANK_ID, "") ?: ""
//        set(value) {
//            editor.apply {
//                putString(BANK_ID, value)
//                commit()
//            }
//        }
//
//    var bank_photo: String
//        get() = pref.getString(BANK_PHOTO, "") ?: ""
//        set(value) {
//            editor.apply {
//                putString(BANK_PHOTO, value)
//                commit()
//            }
//        }
//
//    var bank_name: String
//        get() = pref.getString(BANK_NAME, "") ?: ""
//        set(value) {
//            editor.apply {
//                putString(BANK_NAME, value)
//                commit()
//            }
//        }
//
//    var bank_cabang: String
//        get() = pref.getString(BANK_CABANG, "") ?: ""
//        set(value) {
//            editor.apply {
//                putString(BANK_CABANG, value)
//                commit()
//            }
//        }
//
//    var bank_rekening: String
//        get() = pref.getString(BANK_REKENING, "") ?: ""
//        set(value) {
//            editor.apply {
//                putString(BANK_REKENING, value)
//                commit()
//            }
//        }
//
//    var bank_account_name: String
//        get() = pref.getString(BANK_ACCOUNT_NAME, "") ?: ""
//        set(value) {
//            editor.apply {
//                putString(BANK_ACCOUNT_NAME, value)
//                commit()
//            }
//        }

    var vehicle_plate: String
        get() = pref.getString(VEHICLE_PLATE, "") ?: ""
        set(value) {
            editor.apply {
                putString(VEHICLE_PLATE, value)
                commit()
            }
        }

    var vehicle_color: String
        get() = pref.getString(VEHICLE_COLOR, "") ?: ""
        set(value) {
            editor.apply {
                putString(VEHICLE_COLOR, value)
                commit()
            }
        }

    var vehicle_brand: String
        get() = pref.getString(VEHICLE_BRAND, "") ?: ""
        set(value) {
            editor.apply {
                putString(VEHICLE_BRAND, value)
                commit()
            }
        }

    var vehicle_type: String
        get() = pref.getString(VEHICLE_TYPE, "") ?: ""
        set(value) {
            editor.apply {
                putString(VEHICLE_TYPE, value)
                commit()
            }
        }

    var vehicle_kind: String
        get() = pref.getString(VEHICLE_KIND, "") ?: ""
        set(value) {
            editor.apply {
                putString(VEHICLE_KIND, value)
                commit()
            }
        }

    var sim_document: String
        get() = pref.getString(SIM_DOCUMENT, "") ?: ""
        set(value) {
            editor.apply {
                putString(SIM_DOCUMENT, value)
                commit()
            }
        }

    var sim_document_id: String
        get() = pref.getString(SIM_DOCUMENT_ID, "") ?: ""
        set(value) {
            editor.apply {
                putString(SIM_DOCUMENT_ID, value)
                commit()
            }
        }

    var stnk_document: String
        get() = pref.getString(STNK_DOCUMENT, "") ?: ""
        set(value) {
            editor.apply {
                putString(STNK_DOCUMENT, value)
                commit()
            }
        }

    var skck_document: String
        get() = pref.getString(SKCK_DOCUMENT, "") ?: ""
        set(value) {
            editor.apply {
                putString(SKCK_DOCUMENT, value)
                commit()
            }
        }

    var rating: String
        get() = pref.getString(RATING, "") ?: ""
        set(value) {
            editor.apply {
                putString(RATING, value)
                commit()
            }
        }

    var job: String
        get() = pref.getString(JOB, "") ?: ""
        set(value) {
            editor.apply {
                putString(JOB, value)
                commit()
            }
        }

    var status: String
        get() = pref.getString(STATUS, "") ?: ""
        set(value) {
            editor.apply {
                putString(STATUS, value)
                commit()
            }
        }

    var status_config_driver: String
        get() = pref.getString(STATUS_CONFIG_DRIVER, "") ?: ""
        set(value) {
            editor.apply {
                putString(STATUS_CONFIG_DRIVER, value)
                commit()
            }
        }

    var balance: String
        get() = pref.getString(BALANCE, "") ?: ""
        set(value) {
            editor.apply {
                putString(BALANCE, value)
                commit()
            }
        }

    var poin: String
        get() = pref.getString(POIN, "") ?: ""
        set(value) {
            editor.apply {
                putString(POIN, value)
                commit()
            }
        }

    var service_id: Int
        get() = pref.getInt(SERVICE_ID, 0)
        set(value) {
            editor.apply {
                putInt(SERVICE_ID, value)
                commit()
            }
        }

    var flag_update: Boolean
        get() = pref.getBoolean(FLAG_UPDATE, false)
        set(value) {
            editor.apply {
                putBoolean(FLAG_UPDATE, value)
                commit()
            }
        }

    var flag_reset_password: Boolean
        get() = pref.getBoolean(FLAG_RESET_PASSWORD, false)
        set(value) {
            editor.apply {
                putBoolean(FLAG_RESET_PASSWORD, value)
                commit()
            }
        }

    var flag_create_password: Boolean
        get() = pref.getBoolean(FLAG_CREATE_PASSWORD, false)
        set(value) {
            editor.apply {
                putBoolean(FLAG_CREATE_PASSWORD, value)
                commit()
            }
        }

//    var current_button: String
//        get() = pref.getString(CURRENT_BUTTON, context.getString(R.string.menuju_lokasi_penjemputan)) ?: ""
//        set(value) {
//            editor.apply {
//                putString(CURRENT_BUTTON, value)
//                commit()
//            }
//        }

    var temp_transaction_id: String
        get() = pref.getString(TEMP_TRANSACTION_ID, "167") ?: ""
        set(value) {
            editor.apply {
                putString(TEMP_TRANSACTION_ID, value)
                commit()
            }
        }

    var trip: String
        get() = pref.getString(TEMP_TRIP, "1") ?: ""
        set(value) {
            editor.apply {
                putString(TEMP_TRIP, value)
                commit()
            }
        }

    var app_settings: String
        get() = pref.getString(APP_SETTINGS, "") ?: ""
        set(value) {
            editor.apply {
                putString(APP_SETTINGS, value)
                commit()
            }
        }

    var app_id_verihubs: String
        get() = pref.getString(APP_ID_VERIHUBS, "") ?: ""
        set(value) {
            editor.apply {
                putString(APP_ID_VERIHUBS, value)
                commit()
            }
        }

    var api_key_verihubs: String
        get() = pref.getString(API_KEY_VERIHUBS, "") ?: ""
        set(value) {
            editor.apply {
                putString(API_KEY_VERIHUBS, value)
                commit()
            }
        }

    val is_login: Boolean
    get() = pref.getBoolean(KEY_LOGIN, false)

    val get_token: String
    get() = pref.getString(KEY_TOKEN, "") ?: ""

    var token: String
        get() = pref.getString(KEY_TOKEN, "") ?: ""
        set(value) {
            editor.apply {
                putString(KEY_TOKEN, value)
                commit()
            }
        }

    fun logout(){
        editor.apply {
            clear()
            putBoolean(ON_BOARDING, true)
            commit()
        }
    }

}