package com.styl.phome.modules.main.setupwifi.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.setupwifi.ISetUpWifiContact
import com.styl.phome.modules.main.setupwifi.presenter.SetUpWifiPresenter
import kotlinx.android.synthetic.main.fragment_set_up_wifi.*


class SetUpWifiFragment : BaseFragment(), View.OnClickListener, ISetUpWifiContact.IView {
    companion object {

        fun newInstance(): SetUpWifiFragment {
            return SetUpWifiFragment()
        }
    }

    private var presenter: SetUpWifiPresenter? = SetUpWifiPresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_set_up_wifi
    }

    override fun init() {
        bt_connect.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_connect -> {
                presenter?.onMyHome()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}