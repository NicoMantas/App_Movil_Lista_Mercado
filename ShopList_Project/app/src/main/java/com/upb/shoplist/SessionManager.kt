package com.upb.shoplist

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("SessionData", Context.MODE_PRIVATE)

    fun saveUserSession(userName: String, userEmail: String) {
        prefs.edit().apply {
            putBoolean("isLoggedIn", true)
            putString("userName", userName)
            putString("userEmail", userEmail)
            apply()
        }
    }

    fun getUserName(): String = prefs.getString("userName", "User") ?: "User"

    fun getUserEmail(): String = prefs.getString("userEmail", "") ?: ""

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}