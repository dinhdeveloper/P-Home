package com.styl.phome.modules.main.machinemode.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.machinemode.IMachineModeContact
import com.styl.phome.modules.main.machinemode.presenter.MachineModePresenter


class MachineModeFragment : BaseFragment(), View.OnClickListener, IMachineModeContact.IView {
    companion object {
        fun newInstance(): MachineModeFragment {
            return MachineModeFragment()
        }
    }

    private var presenter: MachineModePresenter? = MachineModePresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_machine_mode
    }

    override fun init() {
    }

    override fun onClick(v: View?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}