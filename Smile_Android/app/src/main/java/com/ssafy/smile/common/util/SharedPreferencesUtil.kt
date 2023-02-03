package com.ssafy.smile.common.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.ssafy.smile.Application
import com.ssafy.smile.domain.model.Types


class SharedPreferencesUtil (context: Context) {
    companion object {
        private const val SHARED_PREFERENCES_NAME = "Application_Preferences"
        private const val AUTH_TOKEN = "AuthToken"
        private const val AUTH_TIME = "AuthTime"
        private const val FCM_TOKEN = "FCMToken"
        private const val ROLE = "Role"
        private const val USER_ID = "UserId"
    }

    var preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun putAuthToken(authToken: String) {
        preferences.edit {
            putString(AUTH_TOKEN, authToken)
            apply()
        }
        Application.authToken = Application.sharedPreferences.getAuthToken()
    }

    fun getAuthToken(): String? = preferences.getString(AUTH_TOKEN, null)
    fun removeAuthToken() {
        preferences.edit {
            remove(AUTH_TOKEN)
            apply()
        }
        Application.authToken = Application.sharedPreferences.getAuthToken()
    }

    fun putAuthTime(authTime: Long) {
        preferences.edit {
            putLong(AUTH_TIME, authTime)
            apply()
        }
        Application.authTime = Application.sharedPreferences.getAuthTime()
    }

    fun getAuthTime(): Long = preferences.getLong(AUTH_TIME, 0)
    fun removeAuthTime() {
        preferences.edit {
            remove(AUTH_TIME)
            apply()
        }
        Application.authTime = Application.sharedPreferences.getAuthTime()
    }

    fun putFCMToken(fcmToken: String) {
        preferences.edit {
            putString(FCM_TOKEN, fcmToken)
            apply()
        }
        Application.fcmToken = Application.sharedPreferences.getFCMToken()
    }

    fun getFCMToken(): String? = preferences.getString(FCM_TOKEN, null)

    fun removeFCMToken() {
        preferences.edit {
            remove(FCM_TOKEN)
            apply()
        }
        Application.fcmToken = Application.sharedPreferences.getFCMToken()
    }

    fun putRole(role: Types.Role) {
        preferences.edit {
            putString(ROLE, role.getValue())
            apply()
        }
        Application.role = Application.sharedPreferences.getRole()
    }

    fun getRole(): String? = preferences.getString(ROLE, null)

    private fun removeRole() {
        preferences.edit {
            remove(ROLE)
            apply()
        }
        Application.role = Application.sharedPreferences.getRole()
    }

    fun changeRole(role: Types.Role){
        removeRole()
        putRole(role)
    }

    fun putUserId(userId: Long) {
        preferences.edit {
            putLong(USER_ID, userId)
            apply()
        }
        Application.userId = Application.sharedPreferences.getUserId()
    }

    fun getUserId(): Long = preferences.getLong(USER_ID, -1L)

    private fun removeUserId() {
        preferences.edit {
            remove(USER_ID)
            apply()
        }
        Application.userId = Application.sharedPreferences.getUserId()
    }

    fun removeAllInfo(){
        removeAuthToken()
        removeAuthTime()
        removeFCMToken()
        removeRole()
        removeUserId()
        Log.d("싸피", "removeAllInfo: ${getAuthToken()}")
    }

}