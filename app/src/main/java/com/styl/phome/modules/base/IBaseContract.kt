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

import androidx.annotation.StringRes
import com.styl.phome.entities.BaseResponse

interface IBaseContract {

    interface IBaseView {

        fun showLoading(messageResId: Int?)
        fun dismissLoading()
        fun <T> showErrorMessage(response: BaseResponse<T>)
        fun showErrorMessage(@StringRes messageResId: Int)
    }

    interface IBasePresenter {

        fun onDestroy()
    }

    interface IBaseInteractor {

        fun onDestroy()
    }

    interface IBaseInteractorOutput<T> {

        fun onSuccess(data: T?)
        fun onError(data: BaseResponse<T>)
    }

    interface IBaseRouter {

    }
}