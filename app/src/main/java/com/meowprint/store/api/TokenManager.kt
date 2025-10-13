package com.meowprint.store.api

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val sp: SharedPreferences =
        context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)

    // Guarda el token de autenticación
    fun saveToken(token: String) {
        sp.edit().putString(Constants.KEY_TOKEN, token).apply()
    }

    // Obtiene el token actual
    fun getToken(): String? = sp.getString(Constants.KEY_TOKEN, null)

    // Guarda nombre y correo del usuario
    fun saveUser(name: String?, email: String?) {
        sp.edit()
            .putString(Constants.KEY_USER_NAME, name ?: "")
            .putString(Constants.KEY_USER_EMAIL, email ?: "")
            .apply()
    }

    // Obtiene nombre y correo del usuario
    fun getUserName(): String = sp.getString(Constants.KEY_USER_NAME, "") ?: ""
    fun getUserEmail(): String = sp.getString(Constants.KEY_USER_EMAIL, "") ?: ""

    // Limpia todos los datos de sesión
    fun clear() {
        sp.edit().clear().apply()
    }
}
