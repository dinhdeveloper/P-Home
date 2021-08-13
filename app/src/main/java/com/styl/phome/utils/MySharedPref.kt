/*
 * (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved
 * This source code and any compilation or derivative thereof is the sole
 * property of STYL Solutions Pte. Ltd. and is provided pursuant to a
 * Software License Agreement.  This code is the proprietary information of
 * STYL Solutions Pte. Ltd. and is confidential in nature. Its use and
 * dissemination by any party other than STYL Solutions Pte. Ltd. is strictly
 * limited by the confidential information provisions of the Agreement
 * referenced above.
 */
package com.styl.phome.utils

import android.content.Context
import com.styl.phome.BuildConfig

class MySharedPref(context: Context?) {

    companion object {
        private const val PREFERENCE_NAME = "phome"
        private const val KEY_FIRST_TIME_INSTALL =
            BuildConfig.APPLICATION_ID + ".keys.KEY_FIRST_TIME_INSTALL"
        private const val KEY_TOKEN = BuildConfig.APPLICATION_ID + ".keys.KEY_TOKEN"
        private const val KEY_DEVICE_ID = BuildConfig.APPLICATION_ID + ".keys.KEY_DEVICE_ID"
        private const val KEY_EXPIRY_TIME = BuildConfig.APPLICATION_ID + ".keys.KEY_EXPIRY_TIME"
        private const val KEY_USER_NAME = BuildConfig.APPLICATION_ID + ".keys.KEY_USER_NAME"

        fun clearUserCache(context: Context?) {
            MySharedPref(context).token = ""
            MySharedPref(context).expiryTime = 0
            MySharedPref(context).deviceId = ""
        }

        fun cacheUserData(context: Context?, expiryTime: Long?, token: String?, userName: String?) {
            MySharedPref(context).expiryTime = expiryTime
            MySharedPref(context).token = token
            MySharedPref(context).userName = userName
        }
    }

    private var pref = context?.getSharedPreferences(PREFERENCE_NAME, 0)

    var isFirstTimeInstall: Boolean?
        get() = pref?.getBoolean(KEY_FIRST_TIME_INSTALL, true)
        set(value) {
            val editor = pref?.edit()
            editor?.putBoolean(KEY_FIRST_TIME_INSTALL, value ?: false)
            editor?.apply()
        }

    var token: String?
        get() = pref?.getString(KEY_TOKEN, "")
        set(value) {
            val editor = pref?.edit()
            editor?.putString(KEY_TOKEN, value)
            editor?.apply()
        }

    var deviceId: String?
        get() = pref?.getString(KEY_DEVICE_ID, "")
        set(value) {
            val editor = pref?.edit()
            editor?.putString(KEY_DEVICE_ID, value)
            editor?.apply()
        }

    var expiryTime: Long?
        get() = pref?.getLong(KEY_EXPIRY_TIME, 0)
        set(value) {
            val editor = pref?.edit()
            editor?.putLong(KEY_EXPIRY_TIME, value ?: 0)
            editor?.apply()
        }

    var userName: String?
        get() = pref?.getString(KEY_USER_NAME, "")
        set(value) {
            val editor = pref?.edit()
            editor?.putString(KEY_USER_NAME, value)
            editor?.apply()
        }
}