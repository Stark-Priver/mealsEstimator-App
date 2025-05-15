/*
 * Copyright 2022 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joelkanyi.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.capitalize
import com.google.gson.Gson
import com.joelkanyi.common.model.ErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import retrofit2.HttpException
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.stringToList(): List<String> {
    return this.split("\r\n").filter { !it.matches(Regex("[0-9]+")) }.filter { !it.isNullOrBlank() }
}

fun Context.imageUriToImageBitmap(uri: Uri): Bitmap {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images
            .Media.getBitmap(contentResolver, uri)
    } else {
        val source = ImageDecoder
            .createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}

fun convertMinutesToHours(minutes: Int): String {
    return if (minutes >= 60) {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        if (hours == 1) {
            if (remainingMinutes == 0) {
                "$hours hr"
            } else {
                "$hours hr $remainingMinutes mins"
            }
        } else {
            if (remainingMinutes == 0) {
                "$hours hrs"
            } else {
                "$hours hrs $remainingMinutes mins"
            }
        }
    } else {
        "$minutes mins"
    }
}

fun showDayCookMessage(): String {
    // Get the time of day
    val date = Date()
    val cal = Calendar.getInstance()
    cal.time = date

    return when (cal[Calendar.HOUR_OF_DAY]) {
        in 12..16 -> {
            "What to cook for lunch?"
        }
        in 17..20 -> {
            "What to cook for dinner?"
        }
        in 21..23 -> {
            "What to cook tonight?"
        }
        else -> {
            "What to cook for breakfast?"
        }
    }
}

fun getTodaysDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val today = Calendar.getInstance().time
    return dateFormat.format(today)
}

fun getAppVersionName(context: Context): String {
    var versionName = ""
    try {
        val info = context.packageManager?.getPackageInfo(context.packageName, 0)
        versionName = info?.versionName ?: ""
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.e(e.message)
    }
    return versionName
}

fun compressImage(bitmap: Bitmap): Bitmap {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
    val byteArray = outputStream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

@Throws(IOException::class)
fun saveImage(context: Context, bitmap: Bitmap): Uri? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )

    return try {
        imageFile.outputStream().use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
        Uri.fromFile(imageFile)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun createImageFile(context: Context): File? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}

fun minutesToMilliseconds(minutes: Int): Long {
    val millisecondsInMinute = 60 * 1000 // 60 seconds * 1000 milliseconds
    return minutes * millisecondsInMinute.toLong()
}

fun convertMillisecondsToTimeString(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60) % 60)
    val hours = (millis / (1000 * 60 * 60) % 24)
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}

fun calendarLocalDates(): List<LocalDate> {
    val thisYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    val lastYear = thisYear - 1
    val nextYear = thisYear + 1
    val dates = mutableListOf<LocalDate>()
    for (i in 0..365) {
        dates += LocalDate(thisYear, 1, 1).plus(i, DateTimeUnit.DAY)
    }
    for (i in 0..365) {
        dates += LocalDate(lastYear, 1, 1).plus(i, DateTimeUnit.DAY)
    }
    for (i in 0..365) {
        dates += LocalDate(nextYear, 1, 1).plus(i, DateTimeUnit.DAY)
    }
    return dates
}

fun LocalDate.prettyPrintedMonthAndYear(): String {
    return "${this.month.name.lowercase().capitalize(androidx.compose.ui.text.intl.Locale.current).substring(0, 3)} ${this.year}"
}
