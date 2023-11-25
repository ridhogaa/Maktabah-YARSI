package com.maktabah.maktabahyarsi.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.maktabah.maktabahyarsi.R
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun ImageView.loadImage(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.no_image_placeholder)
        .error(R.drawable.no_image_placeholder)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun String.decodeJwtPayload(): String {
    val parts = this.split('.')
    if (parts.size != 3) {
        throw IllegalArgumentException("Invalid JWT format")
    }

    val encodedPayload = parts[1]
    val decodedPayload = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getUrlDecoder().decode(encodedPayload)
    } else {
        android.util.Base64.decode(encodedPayload, android.util.Base64.URL_SAFE)
    }
    return String(decodedPayload, Charsets.UTF_8)
}

fun String.isJwtExpired(): Boolean {
    val payload = this.decodeJwtPayload()
    val jsonObject = JSONObject(payload)
    if (jsonObject.has("exp")) {
        val expirationTime = jsonObject.getLong("exp") * 1000
        val currentTime = Date().time
        return currentTime > expirationTime
    }
    return false
}

fun String.getDataJwt(): JSONObject = JSONObject(this.decodeJwtPayload())

fun showSnackBar(view: View, text: String) =
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        .show()

fun getMonthNow(): String =
    SimpleDateFormat("MMMM", Locale.getDefault()).format(Calendar.getInstance().time).lowercase()

fun getYearNow(): String =
    SimpleDateFormat("YYYY", Locale.getDefault()).format(Calendar.getInstance().time).lowercase()

@SuppressLint("ConstantLocale")
val currentDate: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)