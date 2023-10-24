package email.phone.phoneverification.utils

import email.phone.phoneverification.ui.utils.justTry
import java.text.SimpleDateFormat
import java.util.*

fun convertStringFromTimestamp(timestamp: Long): String? {
    val date = Date(timestamp)
    val simpleDateFormat = SimpleDateFormat("MMM DD", Locale.getDefault())
    var str = ""
    justTry {
        str = simpleDateFormat.format(date)
    }
    return str
}

fun String.convertStringToInputFormat(simpleDateFormatPattern: String): Date? {
    val date = Date()
    val simpleDateFormat = SimpleDateFormat(simpleDateFormatPattern, Locale.getDefault())
    var value: Date? = null
    justTry {
        value = simpleDateFormat.parse(this)
    }
    return value
}