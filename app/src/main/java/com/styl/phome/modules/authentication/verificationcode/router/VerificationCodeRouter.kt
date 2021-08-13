package com.styl.phome.modules.authentication.verificationcode.router

import com.styl.phome.R
import com.styl.phome.modules.authentication.newpassword.view.NewPasswordFragment
import com.styl.phome.modules.authentication.verificationcode.IVerificationCodeContract
import com.styl.phome.modules.base.BaseFragment

class VerificationCodeRouter(private val fragment: BaseFragment?) : IVerificationCodeContract.IRouter {

    override fun navigateToNewPassword() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                NewPasswordFragment.newInstance()
            )
        }
    }
}