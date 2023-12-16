package com.surelabsid.mrjempootdriver.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun showToast(context: Context, message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showError(context: Context?, it: Throwable?) {
    context?.let { it1 -> showToast(it1, it?.message) }
}

fun loadingFragment(it: Boolean?, progressBar: ProgressBar) {
    if (it == true)
        progressBar.show()
    else
        progressBar.hide()
}

fun showShimmerList(it: Boolean?, shimmer: ShimmerFrameLayout, data: RecyclerView) {
    if (it == true) {
        data.hide()
        shimmer.show()
        shimmer.startShimmer()
    } else {
        shimmer.stopShimmer()
        shimmer.hide()
        data.show()
    }
}

fun showLoading(it: Boolean?, fragmentManager: FragmentManager, dialog: DialogFragment) {
    if (it == true)
        dialog.show(fragmentManager, "")
    else
        dialog.dismiss()
}

fun requiredEditText(it: Boolean?, editText: EditText, message: String?) {
    if (it == true)
        editText.error = message
}

fun requiredTextInputEditText(it: Boolean?, textInputEditText: TextInputEditText, message: String?) {
    if (it == true)
        textInputEditText.error = message
}

fun requiredTextInputLayout(it: Boolean?, editText: TextInputLayout, message: String?) {
    if (it == true)
        editText.error = message
}

fun requiredToast(context: Context, it: Boolean?, message: String?) {
    if (it == true) {
        showToast(context, message)
    }
}

fun convertByteArray(file_path: String): ByteArray {
    val bitmap = BitmapFactory.decodeFile(file_path)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
    return baos.toByteArray()
}

fun encodeBase64(file_path: String?): String{
    return Base64.encodeToString(convertByteArray(file_path ?: ""), Base64.DEFAULT)
}

fun validationEmail(email: String?): Boolean {
    val emailPattern =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val pattern = Pattern.compile(emailPattern)
    val matcher = pattern.matcher(email.toString())
    return matcher.matches()
}

fun validationPassword(password: String?): Boolean {
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    val pattern = Pattern.compile(PASSWORD_PATTERN)
    val matcher = pattern.matcher(password)
    return matcher.matches()
}

fun toRinggit(amount: Double?): String {
    val COUNTRY = "MY"
    val LANGUAGE = "en"
    val str = NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(amount)
    return str
}

fun toRupiah(amount: Double?): String {
    val COUNTRY = "ID"
    val LANGUAGE = "id"
    val str = NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(amount)
    return str
}

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String?): String {
    val inputFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
    val inputDate = inputFormat.parse(date)

    val pattern = "dd/MM/yyyy HH:mm"
    val sdf = SimpleDateFormat(pattern)

    return sdf.format(inputDate ?: "")
}


@SuppressLint("SimpleDateFormat")
fun formatDateMonth(date: String?): String {
    val inputFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
    val inputDate = inputFormat.parse(date)

    val pattern = "dd.MM.yyyy HH:mm:SS"
    val sdf = SimpleDateFormat(pattern)

    return sdf.format(inputDate ?: "")
}

@SuppressLint("SimpleDateFormat")
fun formatDatePicker(date: String?): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    val inputDate = inputFormat.parse(date)

    val pattern = "yyyy-MM-dd"
    val sdf = SimpleDateFormat(pattern)

    return sdf.format(inputDate ?: "")
}

fun formatDatePickertoShow(date: String?): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    val inputDate = inputFormat.parse(date)

    val pattern = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(pattern)

    return sdf.format(inputDate ?: "")
}

@SuppressLint("SimpleDateFormat")
fun formatDateOnly(date: String?): String {
    val inputFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
    val inputDate = inputFormat.parse(date)

    val pattern = "dd MMM yyyy"
    val sdf = SimpleDateFormat(pattern)

    return sdf.format(inputDate ?: "")
}

@SuppressLint("SimpleDateFormat")
fun formatTimeOnly(date: String?): String {
    val inputFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
    val inputDate = inputFormat.parse(date)

    val pattern = "HH:mm"
    val sdf = SimpleDateFormat(pattern)

    return sdf.format(inputDate ?: "")
}

@SuppressLint("SimpleDateFormat")
fun formatGetMonth(date: String?): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:SS")
    val inputDate = inputFormat.parse(date)

    val pattern = "MM"
    val sdf = SimpleDateFormat(pattern)

    return sdf.format(inputDate ?: "")
}



