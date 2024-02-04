package com.ssafy.tranvel.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(
    @ApplicationContext context: Context,
) {
    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("tranvel", Context.MODE_PRIVATE)
    }

    val prefs by lazy { getPreference(context) }
    private val editor by lazy { prefs.edit() }

    fun getString(key: String, defValue: String? = null): String? {
        return prefs.getString(key, defValue)
    }

    fun putString(key: String, data: String?) {
        editor.putString(key, data)
        editor.apply()
    }
}