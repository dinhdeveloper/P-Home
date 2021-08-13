package com.styl.phome.modules.main.registration.router

import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.machinemode.view.MachineModeFragment
import com.styl.phome.modules.main.registration.IRegistrationContact

class RegistrationRouter(private var fragment: BaseFragment?) : IRegistrationContact.IRouter {
    override fun navigatePhoneFound() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                MachineModeFragment.newInstance()
            )
        }
    }
}