package com.styl.phome.modules.authentication.signup

import com.styl.phome.modules.base.IBaseContract

interface ISignUpContract {
    interface IView : IBaseContract.IBaseView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun onOpenVerificationCode()
    }

    interface IRouter {
        fun navigateToVerificationCode()
    }
}