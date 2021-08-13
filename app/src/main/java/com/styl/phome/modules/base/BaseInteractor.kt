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
package com.styl.phome.modules.base

import com.styl.phome.R
import com.styl.phome.entities.BaseResponse
import com.styl.phome.entities.GeneralException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseInteractor {

    fun <T> onResponse(
        resultType: Response<T>,
        interactorOutput: IBaseContract.IBaseInteractorOutput<T>?
    ) {
        if (resultType.isSuccessful) {
            var errorCode = -1
            try {
                errorCode = Integer.parseInt(resultType.headers().get("errorCode") ?: "")
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            val errorMessage = resultType.headers().get("errorMessage") ?: ""

            if (errorCode == 0) {
                val data = resultType.body()
                interactorOutput?.onSuccess(data)
            } else {
                val baseResponse = BaseResponse<T>(errorCode, errorMessage, null)
                interactorOutput?.onError(baseResponse)
            }
        } else {
            val errorCode = resultType.code()
            val errorMessage = resultType.headers().get("errorMessage") ?: ""
            val baseResponse = BaseResponse<T>(errorCode, errorMessage, null)
            interactorOutput?.onError(baseResponse)
        }
    }

    fun <T> isMapSuccess(resultType: Response<T>): Boolean {
        if (resultType.isSuccessful) {
            val errorCode = Integer.parseInt(resultType.headers().get("errorCode") ?: "")
            val errorMessage = resultType.headers().get("errorMessage")

            if (errorCode == 0) {
                return true
            } else {
                throw GeneralException(errorCode, errorMessage)
            }
        } else {
            throw GeneralException(resultType.code(), resultType.message())
        }
    }

    fun <T> onError(t: Throwable, interactorOutput: IBaseContract.IBaseInteractorOutput<T>?) {
        val errorCode = -1
        if (t is GeneralException) {
            val baseResponse = BaseResponse<T>(
                t.code ?: 0, t.message, 0
            )
            interactorOutput?.onError(baseResponse)
        } else if (t is ConnectException || t is SocketTimeoutException ||
            t is UnknownHostException || t is IOException
        ) {
            val baseResponse = BaseResponse<T>(
                errorCode, null,
                R.string.cannot_connect_server
            )
            interactorOutput?.onError(baseResponse)
        } else {
            val errorMessage = t.message ?: ""
            val baseResponse = BaseResponse<T>(errorCode, errorMessage, 0)
            interactorOutput?.onError(baseResponse)
        }
    }
}