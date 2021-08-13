package com.styl.phome.modules.authentication.signin.presenter

import com.styl.phome.modules.authentication.signin.ISignInContract
import com.styl.phome.modules.authentication.signin.router.SignInRouter
import com.styl.phome.modules.base.BaseFragment

class SignInPresenter(private var view: ISignInContract.IView?) : ISignInContract.IPresenter {
    private var router: SignInRouter? = SignInRouter(view as? BaseFragment)

    override fun onLogin() {

    }

    override fun onOpenVerificationCode() {
        router?.navigateToVerificationCode()
    }

    override fun onOpenForgotPassword() {
        router?.navigateToForgotPassword()
    }

    override fun onOpenSignUp() {
        router?.navigateToSignUp()
    }

    override fun onDestroy() {
        view = null
        router = null
    }

}