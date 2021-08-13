package com.styl.phome.modules.main.setupwifi.router

import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.myhome.view.MyHomeFragment
import com.styl.phome.modules.main.registration.view.RegistrationFragment
import com.styl.phome.modules.main.setupwifi.ISetUpWifiContact

class SetUpWifiRouter(private var fragment: BaseFragment?) : ISetUpWifiContact.IRouter {
    override fun navigateRegistration() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                RegistrationFragment.newInstance()
            )
        }
    }

    override fun navigateMyHome() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                MyHomeFragment.newInstance()
            )
        }
    }
}