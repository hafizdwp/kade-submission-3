package me.hafizdwp.kade_submission_3.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import me.hafizdwp.kade_submission_3.MyApp

/**
 * @author hafizdwp
 * 11/07/19
 **/
object Pref {

    const val PREF_EXAMPLE = "pref_example"


    private val prefs
        get() = getSharedPreferences(MyApp.getContext())

    var listExample: String?
        get() = prefs[PREF_EXAMPLE]
        set(value) {
            prefs[PREF_EXAMPLE] = value
        }




    private inline fun SharedPreferences.edit(operation: SharedPreferences.Editor.() -> Unit) {
        edit().apply {
            operation(this)
            apply()
        }
    }

    // TODO: Use this instead to refresh prefs
    fun SharedPreferences.clearPref() {
        edit().clear().apply()
    }

    fun getSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    inline operator fun <reified T : Any?> SharedPreferences.get(key: String, defaultValue: T? = null): T? =
        when (T::class) {
            String::class -> getString(key, defaultValue as? String ?: "") as? T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as? T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as? T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1F) as? T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1L) as? T?
            else -> throw UnsupportedOperationException("Pref factory get not yet implemented")
        }

    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { putString(key, value) }
            is Int -> edit { putInt(key, value) }
            is Boolean -> edit { putBoolean(key, value) }
            is Float -> edit { putFloat(key, value) }
            is Long -> edit { putLong(key, value) }
            else -> throw UnsupportedOperationException("Pref factory set not yet implemented")
        }
    }
}