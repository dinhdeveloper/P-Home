package com.styl.phome.modules.authentication.forgotpassword.presenter

import com.styl.phome.modules.authentication.forgotpassword.IForgotPasswordContract
import com.styl.phome.modules.authentication.forgotpassword.router.ForgotPasswordRouter
import com.styl.phome.modules.base.BaseFragment

class ForgotPasswordPresenter(private var view: IForgotPasswordContract.IView?) :
    IForgotPasswordContract.IPresenter {
    private var router: ForgotPasswordRouter? = ForgotPasswordRouter(view as? BaseFragment)

    override fun onOpenVerificationCode() {
        router?.navigateToVerificationCode()
    }

    override fun onDestroy() {
        view = null
        router = null
    }
}