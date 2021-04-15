package com.sample.a1kosmos.data.preferances

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.sample.a1kosmos.data.model.user.UserData
import javax.inject.Inject


private const val PAGE = "page"


class PreferenceProvider @Inject constructor(private val preferences: SharedPreferences) {

    fun savePage(data: UserData) {
        preferences.edit(true) {
            val gson = Gson()
            val jsonObject = gson.toJson(data)
            putString(PAGE, jsonObject)
            apply()
        }
    }

    fun getSavePage(): UserData? {
        val json: String = preferences.getString(PAGE, null).toString()
        val gson = Gson()
        return gson.fromJson(json, UserData::class.java)

    }
}