package com.styl.phome.modules.main.home.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.authentication.signin.ISignInContract
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.home.IWelcomeContract
import com.styl.phome.modules.main.home.presenter.HomePresenter
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment(), View.OnClickListener, ISignInContract.IView,
    IWelcomeContract.IView {

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var presenter: HomePresenter? = HomePresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.home_fragment
    }

    override fun init() {
        btnAddPhome.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAddPhome -> {
                presenter?.onAddPHome()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}