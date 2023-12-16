package com.surelabsid.mrjempootdriver.utils

class Constant {

//    current : 21

    companion object {
        object url {
            const val BASE = "https://ezzgogo.com/admin"
            const val BASE_IMAGE = "$BASE/images"
            const val BASE_URL = "$BASE/api/"
            const val BASE_URL_DRIVER_PHOTO = "$BASE_IMAGE/driverphoto/"
            const val BASE_URL_CUSTOMER_PHOTO = "$BASE_IMAGE/customer/"
            const val BASE_URL_CHATCS_PHOTO = "$BASE_IMAGE/chatcs/"
            const val BASE_URL_SERVICE_IMAGE = "$BASE_IMAGE/service/"
            const val BASE_URL_RECEIVE_PACKET_PHOTO = "$BASE_IMAGE/photofile/packet/"
            const val BASE_URL_ITEM_PHOTO = "$BASE_IMAGE/itemphoto/"
            const val BASE_URL_ITEM_HEALTH = "$BASE_IMAGE/photofile/health/"

            const val BASE_URL_VERIHUBS = "https://api.verihubs.com/"

            const val FAQ = "https://ezzgogo.com/admin/webview/faq"
        }

        object value {
            const val ROUND_IMAGE = 15
        }

        object permission {
            const val ACCESS_LOCATION = 18
            const val CAMERA = 17
            const val STORAGE = 30
            const val STORAGE_AND_CAMERA = 3017
            const val STORAGE_AND_CAMERA_AND_CALL = 301716
            const val READ_PHONE_STATE = 19
            const val CALL = 16
            const val LIVE = 20
        }

        object location {
            const val INTERVAL_UPDATE_LOCATION: Int = 1
        }

        object code_request {
            const val CAMERA_CODE = 0
            const val GALLERY_CODE = 15
            const val CAMERA_SIM_CODE = 1
            const val CAMERA_STNK_CODE = 2
            const val CAMERA_SKCK_CODE = 3
            const val CUSTOMER_RESULT = 5
            const val BANK_RESULT = 6
            const val WITHDRAW_RESULT = 7

            const val DOCUMENT_VAKSIN_RESULT = 8
            const val DOCUMENT_BEBAS_COVID_RESULT = 9
            const val DOCUMENT_SWAB_RESULT = 10

            const val BAYAR_MERCHANT_RESULT = 11
            const val AMBIL_FOTO_FINGER_RESULT = 12
            const val BAYAR_MR_JEMPOOT_RESULT = 13
            const val KIRIM_STRUK_RESULT = 14
        }

        object transaction_status {
            const val FIND_DRIVER = 1
            const val DRIVER_FOUND = 2
            const val PROCESS = 3
            const val COMPLETE = 4
            const val CANCEL = 5
        }

        object item_view_type {
            const val ITEM_WALLET = 1
            const val ITEM_TOPUP = 2
            const val ITEM_WALLET_EMPTY = 3
            const val ITEM_PENARIKAN = 4


            const val ITEM_REKENING = 5

            const val ITEM_NOTIFIKASI = 6

            const val ITEM_POIN = 7
            const val ITEM_SOUVENIR = 88

            const val ITEM_ULASAN = 9
        }

        object database {
            const val VERSION = 1
            const val NAME = "DB_MR_JEMPOOT_APP"
        }

        object wallet_status {
            const val PENDING = 1
            const val SUCCESS = 2
            const val FAILURE = 3
            const val CHALLENGE = 4
        }
    }
}