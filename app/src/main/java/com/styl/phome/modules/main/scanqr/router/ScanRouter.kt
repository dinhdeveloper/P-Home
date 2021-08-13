package com.styl.phome.modules.main.scanqr.router

import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.scanqr.IScanContact
import com.styl.phome.modules.main.setupwifi.view.SetUpWifiFragment

class ScanRouter(private var fragment: BaseFragment?) : IScanContact.IRouter {

    override fun navigateDeviceFound() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                SetUpWifiFragment.newInstance()
            )
        }
    }
}