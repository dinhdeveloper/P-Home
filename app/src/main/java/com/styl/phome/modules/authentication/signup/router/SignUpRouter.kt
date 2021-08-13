package com.styl.phome.modules.authentication.signup.router

import com.styl.phome.R
import com.styl.phome.modules.authentication.signup.ISignUpContract
import com.styl.phome.modules.authentication.verificationcode.view.VerificationCodeFragment
import com.styl.phome.modules.base.BaseFragment

class SignUpRouter(private val fragment: BaseFragment?) : ISignUpContract.IRouter {

    override fun navigateToVerificationCode() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                VerificationCodeFragment.newInstance(VerificationCodeFragment.VERIFY_SIGN_UP)
            )
        }
    }
}