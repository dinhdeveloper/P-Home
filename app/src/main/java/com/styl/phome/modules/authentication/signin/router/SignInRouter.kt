package com.styl.phome.modules.authentication.signin.router

import com.styl.phome.R
import com.styl.phome.modules.authentication.forgotpassword.view.ForgotPasswordFragment
import com.styl.phome.modules.authentication.signin.ISignInContract
import com.styl.phome.modules.authentication.signup.view.SignUpFragment
import com.styl.phome.modules.authentication.verificationcode.view.VerificationCodeFragment
import com.styl.phome.modules.base.BaseFragment

class SignInRouter(private val fragment: BaseFragment?) : ISignInContract.IRouter {
    override fun navigateToForgotPassword() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                ForgotPasswordFragment.newInstance()
            )
        }
    }

    override fun navigateToSignUp() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                SignUpFragment.newInstance()
            )
        }
    }

    override fun navigateToVerificationCode() {
        /*fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                VerificationCodeFragment.newInstance()
            )
        }*/
    }

}