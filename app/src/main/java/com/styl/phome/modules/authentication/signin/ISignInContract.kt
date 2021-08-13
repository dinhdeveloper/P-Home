package com.styl.phome.modules.authentication.signin

import com.styl.phome.modules.base.IBaseContract

interface ISignInContract {

    interface IView : IBaseContract.IBaseView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun onLogin()
        fun onOpenForgotPassword()
        fun onOpenSignUp()
        fun onOpenVerificationCode()
    }

    interface IRouter {
        fun navigateToForgotPassword()
        fun navigateToSignUp()
        fun navigateToVerificationCode()
    }
}