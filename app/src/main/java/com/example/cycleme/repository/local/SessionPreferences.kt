package com.example.cycleme.repository.local

import android.content.Context
import androidx.core.content.edit
import com.example.cycleme.model.CheckLogin

internal class SessionPreferences(context: Context) {
    companion object {
        private const val uuid = "id"
        private const val name = "name"
        private const val isLogin = "islogin"
        private const val sessionID = "sessionID"
    }

    private val preferences = context.getSharedPreferences(isLogin, Context.MODE_PRIVATE)

    fun getLogin(): CheckLogin {
        val model = CheckLogin()

        model.isLogin = preferences.getBoolean(isLogin, false)
        model.uuid = preferences.getString(uuid, "")
        model.name = preferences.getString(name, "")
        model.sessionID = preferences.getString(sessionID, "")

        return model
    }

    fun setLogin(data: CheckLogin) {
        preferences.edit {
            putBoolean(isLogin, data.isLogin)
            putString(uuid, data.uuid)
            putString(name, data.name)
            putString(sessionID, data.sessionID)
        }
    }

    fun clearSession() {
        preferences.edit {
            clear()
        }
    }
}