package com.yujin.inColor.Model

import android.content.Context
import android.content.SharedPreferences


object SaveSharedPreference {
    private const val PREF_NAME = "InColor"
    private const val AUTO_SIGN_IN = "AUTO_SIGN_IN"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // 자동로그인 여부 저장
    fun setAutoSignIn(context: Context) {
        val editor = getSharedPreferences(
            context
        ).edit()
        editor.putBoolean(AUTO_SIGN_IN, true)
        editor.apply()
    }

    // 자동로그인 여부 리턴
    fun isAutoSignIn(context: Context): Boolean {
        return getSharedPreferences(
            context
        ).getBoolean(AUTO_SIGN_IN, false)
    }

    // 로그아웃
    fun clearAutoSignIn(context: Context) {
        val editor = getSharedPreferences(
            context
        ).edit()
        editor.putBoolean(AUTO_SIGN_IN, false)
        editor.apply()
    }
}