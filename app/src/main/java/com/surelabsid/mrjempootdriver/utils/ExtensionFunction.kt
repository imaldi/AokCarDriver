package com.surelabsid.mrjempootdriver.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.Image
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.CAMERA_CODE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.code_request.GALLERY_CODE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.ACCESS_LOCATION
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.CAMERA
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.STORAGE
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.STORAGE_AND_CAMERA
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.permission.STORAGE_AND_CAMERA_AND_CALL
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun Context.showAlert(title: String, message: String, cancel: Boolean) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setCancelable(cancel)
        setPositiveButton("OK") { _, _ ->

        }.show()
    }
}

fun Context.showSettingGps() {
    val alertBuilder = AlertDialog.Builder(this)
    alertBuilder.setTitle("GPS Setting")
    alertBuilder.setMessage("GPS is not enabled. Do you want to go to settings menu?")
    alertBuilder.setCancelable(false)
    alertBuilder.setPositiveButton("Setting") { _, _ ->
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
//    alertBuilder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

    alertBuilder.show()
}


//fun Context.askAlert(
//    title: String,
//    message: String,
//    cancelable: Boolean,
//    button_positif: String,
//    action_positif: Unit,
//    button_negative: String,
//    action_negative: Unit
//) {
//    AlertDialog.Builder(this).apply {
//        setTitle(title)
//        setMessage(message)
//        setCancelable(cancelable)
//        setPositiveButton(button_positif) { _, _ ->
//            action_positif
//        }.setNegativeButton(button_negative) { _, _ ->
//            action_negative
//        }.show()
//    }
//}

fun View.showSnackbar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun Activity.moveActivity(destination: Class<out Activity>) {
    finish()
    startActivity(Intent(this, destination))
}

fun Activity.openActivity(destination: Class<out Activity>) {
    startActivity(Intent(this, destination))
}

fun Activity.initPermissionCamera() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.CAMERA,
            ), CAMERA
        )
    }
}

fun Activity.initPermissionStorage() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ), STORAGE
        )
    }
}

fun Activity.initPermissionStorageAndCamera() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
            ), STORAGE_AND_CAMERA
        )
    }
}

fun Activity.initPermissionStorageAndCameraAndCall() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.CALL_PHONE,
            ), STORAGE_AND_CAMERA_AND_CALL
        )
    }
}

fun Activity.initPermissionCall() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.CALL_PHONE,
            ), Constant.Companion.permission.CALL
        )
    }
}

fun Activity.initPermissionReadPhoneState() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.READ_PHONE_STATE,
            ), Constant.Companion.permission.READ_PHONE_STATE
        )
    }
}

fun Activity.initPermissionLocation() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), ACCESS_LOCATION
        )
    }
}

fun Activity.initPermissionBluetooth() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN
            ), 3
        )
    }
}

fun Activity.openCamera(code: Int = CAMERA_CODE) {
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(cameraIntent, code)
}

fun Activity.openGallery() {
    val mimeTypes = arrayOf("image/jpg", "image/jpeg", "image/gif")

    val intent = Intent()
    intent.type = "*/*"
    intent.action = Intent.ACTION_GET_CONTENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    startActivityForResult(
        Intent.createChooser(intent, getString(R.string.choose_image)),
        GALLERY_CODE
    )
}

fun Activity.persistImage(bitmap: Bitmap, name: String): String {
    val filesDir = filesDir
    val imageFile = File(filesDir, name + ".png")

    val image_path = imageFile.path

    val os: OutputStream?
    try {
        os = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        os.flush()
        os.close()
    } catch (e: Exception) {
        Log.e(javaClass.simpleName, getString(R.string.error_writing_bitmap), e)
    }

    return image_path ?: ""
}

fun Activity.getMimeTypeFile(uri: Uri?): String {
    var mimeType = ""
    if (uri?.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
        val cr = getContentResolver()
        mimeType = cr.getType(uri!!)!!
    } else {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
            uri
                .toString()
        )
        mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtension.toLowerCase()
        )!!
    }
    return mimeType
}

fun Bitmap.rotateAndCrop(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 200, 100, 300, 300, matrix, true)
}

fun Bitmap.rotateAndCropPotrait(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 150, 300, 200, 200, matrix, true)
}

fun Image.toBitmap(): Bitmap {
    val yBuffer = planes[0].buffer // Y
    val vuBuffer = planes[2].buffer // VU

    val ySize = yBuffer.remaining()
    val vuSize = vuBuffer.remaining()

    val nv21 = ByteArray(ySize + vuSize)

    yBuffer.get(nv21, 0, ySize)
    vuBuffer.get(nv21, ySize, vuSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun RelativeLayout.show() {
    visibility = View.VISIBLE
}

fun RelativeLayout.hide() {
    visibility = View.INVISIBLE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun LinearLayout.show() {
    visibility = View.VISIBLE
}

fun LinearLayout.hide() {
    visibility = View.GONE
}

fun Button.show() {
    visibility = View.VISIBLE
}

fun Button.hide() {
    visibility = View.GONE
}

fun CardView.show() {
    visibility = View.VISIBLE
}

fun CardView.hide() {
    visibility = View.GONE
}

fun ImageView.show() {
    visibility = View.VISIBLE
}

fun ImageView.hide() {
    visibility = View.INVISIBLE
}

fun CheckBox.show() {
    visibility = View.VISIBLE
}

fun CheckBox.hide() {
    visibility = View.INVISIBLE
}

fun RecyclerView.show() {
    visibility = View.VISIBLE
}

fun RecyclerView.hide() {
    visibility = View.INVISIBLE
}

fun ShimmerFrameLayout.show() {
    visibility = View.VISIBLE
}

fun ShimmerFrameLayout.hide() {
    visibility = View.GONE
}

/*fun Context.myToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}*/

