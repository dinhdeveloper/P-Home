package com.styl.phome.modules.authentication.verificationcode

import com.styl.phome.modules.base.IBaseContract

interface IVerificationCodeContract {

    interface IView : IBaseContract.IBaseView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun onOpenNewPassword()
    }

    interface IRouter {
        fun navigateToNewPassword()
    }
}