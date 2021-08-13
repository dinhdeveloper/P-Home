package com.styl.phome.modules.main.home.router

import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.home.IWelcomeContract
import com.styl.phome.modules.main.instruction.view.InstructionFragment

class HomeRouter(private var fragment: BaseFragment?) : IWelcomeContract.IRouter {
    override fun navigateToAddPHome() {
        fragment?.let {
            it.replaceFragment(
                    it.parentFragmentManager,
                    R.id.container,
                    InstructionFragment.newInstance()
            )
        }
    }

}