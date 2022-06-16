package com.example.programmertyccon.utils

import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesUtil {

    companion object {
        var preferences: SharedPreferences? = null

        fun initialize(preferences: SharedPreferences) {
            this.preferences = preferences
        }

        fun save(key: String, value: Any) {
            if(preferences == null) {
                throw IllegalStateException("preferences must not be null")
            }

            preferences!!.edit {
                when(value) {
                    is Boolean -> putBoolean(key, value)
                    is Int -> putInt(key, value)
                    is String -> putString(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)
                    is Double -> putLong(key, java.lang.Double.doubleToRawLongBits(value))
                }
            }
        }

        fun getBoolean(key: String): Boolean {
            if(preferences == null) {
                throw IllegalStateException("preferences must not be null")
            }
            return preferences!!.getBoolean(key, false)
        }
        fun getInt(key: String): Int {
            if(preferences == null) {
                throw IllegalStateException("preferences must not be null")
            }
            return preferences!!.getInt(key, 0)
        }
        fun getLong(key: String): Long {
            if(preferences == null) {
                throw IllegalStateException("preferences must not be null")
            }
            return preferences!!.getLong(key, 0)
        }
        fun getDouble(key: String): Double {
            if(preferences == null) {
                throw IllegalStateException("preferences must not be null")
            }
            return java.lang.Double.longBitsToDouble(preferences!!.getLong(key, 0))
        }
    }
}