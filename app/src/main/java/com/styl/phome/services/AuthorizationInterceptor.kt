package com.styl.phome.services

import android.text.TextUtils
import com.styl.phome.MainApplication
import com.styl.phome.utils.MySharedPref
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = MySharedPref(MainApplication.applicationContext()).token
        var request = chain.request()
        if (!TextUtils.isEmpty(token)) {
            request = request.newBuilder().addHeader("Authorization", "Bearer $token").build()
        }

        return chain.proceed(request)
    }
}