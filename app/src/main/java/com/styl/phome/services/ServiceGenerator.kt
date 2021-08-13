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
package com.styl.phome.services

import com.styl.phome.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {

    companion object {

        const val DEFAULT_VERSION = ""
        const val DEFAULT_PKCS_VERSION = "20"

        fun <T> createService(
            serviceClass: Class<T>
        ): T {
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(Config.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Config.READ_TIMEOUT, TimeUnit.MILLISECONDS)

            httpClient.addInterceptor(AuthorizationInterceptor())
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }
            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(serviceClass)
        }
    }
}