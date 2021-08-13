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
package com.styl.phome.entities

class BaseResponse<T> {

    var errorCode: Int
    var errorMessage: String?
    var messageResId: Int? = null
    var data: T? = null

    constructor(errorCode: Int, errorMessage: String?, data: T?) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
        this.data = data
    }

    constructor(errorCode: Int, errorMessage: String?, messageResId: Int?) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
        this.messageResId = messageResId
    }
}