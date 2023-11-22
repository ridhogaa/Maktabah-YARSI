package com.maktabah.maktabahyarsi.utils

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun NavController.safeNavigate(
    @IdRes currentDestinationId: Int,
    @IdRes id: Int,
    args: Bundle? = null
) {
    if (currentDestinationId == currentDestination?.id) {
        navigate(id, args)
    }
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
