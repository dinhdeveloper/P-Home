package com.styl.phome.modules.authentication.signup.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.authentication.signup.ISignUpContract
import com.styl.phome.modules.authentication.signup.presenter.SignUpPresenter
import com.styl.phome.modules.base.BaseFragment
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUpFragment : BaseFragment(), View.OnClickListener, ISignUpContract.IView {

    companion object {

        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }

    private var presenter: SignUpPresenter? = SignUpPresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.sign_up_fragment
    }

    override fun init() {
        tvSignIn.setOnClickListener(this)
        btnContinue.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSignIn -> {
                activity?.onBackPressed()
            }
            R.id.btnContinue -> {
                presenter?.onOpenVerificationCode()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}