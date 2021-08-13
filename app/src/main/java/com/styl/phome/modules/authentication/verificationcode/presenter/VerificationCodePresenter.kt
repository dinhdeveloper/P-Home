package com.styl.phome.modules.authentication.verificationcode.presenter

import com.styl.phome.modules.authentication.verificationcode.IVerificationCodeContract
import com.styl.phome.modules.authentication.verificationcode.router.VerificationCodeRouter
import com.styl.phome.modules.base.BaseFragment

class VerificationCodePresenter(private var view: IVerificationCodeContract.IView?) :
    IVerificationCodeContract.IPresenter {

    private var router: VerificationCodeRouter? = VerificationCodeRouter(view as? BaseFragment)
    override fun onOpenNewPassword() {
        router?.navigateToNewPassword()
    }

    override fun onDestroy() {
        router = null
    }
}