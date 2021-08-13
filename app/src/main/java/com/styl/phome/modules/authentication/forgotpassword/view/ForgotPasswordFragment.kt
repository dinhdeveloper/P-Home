package com.styl.phome.modules.authentication.forgotpassword.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.authentication.forgotpassword.IForgotPasswordContract
import com.styl.phome.modules.authentication.forgotpassword.presenter.ForgotPasswordPresenter
import com.styl.phome.modules.base.BaseFragment
import kotlinx.android.synthetic.main.forgot_password_fragment.*

class ForgotPasswordFragment : BaseFragment(), View.OnClickListener, IForgotPasswordContract.IView {

    companion object {

        fun newInstance(): ForgotPasswordFragment {
            return ForgotPasswordFragment()
        }
    }

    private var presenter: ForgotPasswordPresenter? = ForgotPasswordPresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.forgot_password_fragment
    }

    override fun init() {
        btnNext.setOnClickListener {
            presenter?.onOpenVerificationCode()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}