package com.styl.phome.modules.authentication.forgotpassword

import com.styl.phome.modules.base.IBaseContract

interface IForgotPasswordContract {

    interface IView : IBaseContract.IBaseView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun onOpenVerificationCode()
    }

    interface IRouter {
        fun navigateToVerificationCode()
    }
}