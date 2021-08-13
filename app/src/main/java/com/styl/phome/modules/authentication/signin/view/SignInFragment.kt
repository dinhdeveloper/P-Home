package com.styl.phome.modules.authentication.signin.view

import android.content.Intent
import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.authentication.signin.ISignInContract
import com.styl.phome.modules.authentication.signin.presenter.SignInPresenter
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.MainActivity
import kotlinx.android.synthetic.main.sign_in_fragment.*

class SignInFragment : BaseFragment(), View.OnClickListener, ISignInContract.IView {

    companion object {

        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }

    private var presenter: SignInPresenter? = SignInPresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.sign_in_fragment
    }

    override fun init() {
        tvForgotPassword.setOnClickListener(this)
        tvSignUp.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvForgotPassword -> {
                presenter?.onOpenForgotPassword()
            }
            R.id.tvSignUp -> {
                presenter?.onOpenSignUp()
            }
            R.id.btnSignIn -> {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        activity?.let {
            startActivity(Intent(it, MainActivity::class.java))
            it.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            it.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}