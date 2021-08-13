package com.styl.phome.modules.main.machinemode.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.machinemode.IMachineModeContact
import com.styl.phome.modules.main.machinemode.router.MachineModeRouter

class MachineModePresenter(private var view: IMachineModeContact.IView?) :
    IMachineModeContact.IPresenter {
    private var router: MachineModeRouter? = MachineModeRouter(view as? BaseFragment)

    override fun onDestroy() {
        router = null
        view = null
    }
}