package com.styl.phome.modules.authentication.newpassword.view

import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import kotlinx.android.synthetic.main.new_password_fragment.*

class NewPasswordFragment : BaseFragment() {

    companion object {

        fun newInstance(): NewPasswordFragment {
            return NewPasswordFragment()
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.new_password_fragment
    }

    override fun init() {
        btnSubmit.setOnClickListener {
            popToRoot()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        presenter?.onDestroy()
//    }
}