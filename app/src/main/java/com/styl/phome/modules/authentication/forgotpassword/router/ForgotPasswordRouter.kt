package com.styl.phome.modules.authentication.forgotpassword.router

import com.styl.phome.R
import com.styl.phome.modules.authentication.forgotpassword.IForgotPasswordContract
import com.styl.phome.modules.authentication.newpassword.view.NewPasswordFragment
import com.styl.phome.modules.authentication.verificationcode.view.VerificationCodeFragment
import com.styl.phome.modules.base.BaseFragment

class ForgotPasswordRouter(private val fragment: BaseFragment?) : IForgotPasswordContract.IRouter {

    override fun navigateToVerificationCode() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                VerificationCodeFragment.newInstance(VerificationCodeFragment.VERIFY_FORGOT_PASSWORD)
            )
        }
    }
}