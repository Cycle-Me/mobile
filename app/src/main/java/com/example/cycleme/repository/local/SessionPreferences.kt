package com.example.cycleme.repository.local

import android.content.Context
import androidx.core.content.edit
import com.example.cycleme.model.LoginDataOnSession

internal class SessionPreferences(context: Context) {
    companion object {
        private const val isLogin = "islogin"
        private const val uuid = "id"
        private const val name = "name"
        private const val email = "email"
        private const val sessionID = "sessionID"
        private const val password = "password"
    }

    private val preferences = context.getSharedPreferences(isLogin, Context.MODE_PRIVATE)

    fun getLogin(): LoginDataOnSession {
        val model = LoginDataOnSession()

        model.isLogin = preferences.getBoolean(isLogin, false)
        model.uuid = preferences.getString(uuid, "")
        model.name = preferences.getString(name, "")
        model.email = preferences.getString(email, "")
        model.sessionID = preferences.getString(sessionID, "")
        model.password = preferences.getString(password, "")

        return model
    }

    fun setLogin(data: LoginDataOnSession) {
        preferences.edit {
            putBoolean(isLogin, data.isLogin)
            putString(uuid, data.uuid)
            putString(name, data.name)
            putString(email, data.email)
            putString(sessionID, data.sessionID)
            putString(password, data.password)
        }
    }

    fun clearSession() {
        preferences.edit {
            clear()
        }
    }
}