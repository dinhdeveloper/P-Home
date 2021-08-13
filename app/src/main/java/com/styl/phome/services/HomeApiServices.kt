package com.styl.phome.services

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface AuthenticateApiServices {
    @GET("appversion/android")
    fun getAppVersion(): Observable<Response<String>>
}