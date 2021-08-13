package com.styl.phome.modules.authentication.signup.presenter

import com.styl.phome.modules.authentication.signup.ISignUpContract
import com.styl.phome.modules.authentication.signup.router.SignUpRouter
import com.styl.phome.modules.base.BaseFragment

class SignUpPresenter(private var view: ISignUpContract.IView?) :
    ISignUpContract.IPresenter {
    private var router: SignUpRouter? = SignUpRouter(view as? BaseFragment)

    override fun onOpenVerificationCode() {
        router?.navigateToVerificationCode()
    }

    override fun onDestroy() {
        view = null
        router = null
    }
}