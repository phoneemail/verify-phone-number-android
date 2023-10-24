package email.phone.phoneverification.utils

import email.phone.phoneverification.Application
import email.phone.phoneverification.utils.AppConstants.PREF_NAME

/**
 * Created by Pratik on 03/02/21.
 */


fun setPreference(key: String, value: Any) {
    val preference = Application.application?.applicationContext?.getSharedPreferences(PREF_NAME, 0)
    val editor = preference?.edit()

    when (value) {
        is String -> editor?.putString(key, value)
        is Boolean -> editor?.putBoolean(key, value)
        is Int -> editor?.putInt(key, value)
    }
    editor?.commit()
}

inline fun <reified T> getPreference(key: String, defaultValue: T): T {
    val preference = Application.application?.applicationContext?.getSharedPreferences(PREF_NAME, 0)
    return when (T::class) {
        String::class -> preference?.getString(key, defaultValue as String) as T
        Boolean::class -> preference?.getBoolean(key, defaultValue as Boolean) as T
        Int::class -> preference?.getInt(key, defaultValue as Int) as T
        else -> {
            " " as T
        }
    }

}

fun clearPreferences() {
    val preference = Application.application?.applicationContext?.getSharedPreferences(PREF_NAME, 0)
    val editor = preference?.edit()
    editor?.clear()
    editor?.commit()
}


/*
inline fun <reified T> setPreferenceObject(key: String, obj: T) {
    setPreference(key, Gson().toJson(obj))
}

inline fun <reified T> getPreferenceObject(key: String): T {
    return Gson().fromJson(getPreference(key, ""), T::class.java)
}*/
