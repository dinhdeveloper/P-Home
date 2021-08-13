package com.styl.phome.modules.main.setupwifi.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.setupwifi.ISetUpWifiContact
import com.styl.phome.modules.main.setupwifi.router.SetUpWifiRouter

class SetUpWifiPresenter(private var view: ISetUpWifiContact.IView?) :
    ISetUpWifiContact.IPresenter {
    private var router: SetUpWifiRouter? = SetUpWifiRouter(view as? BaseFragment)

    override fun onRegistration() {
        router?.navigateRegistration()
    }

    override fun onMyHome() {
        router?.navigateMyHome()
    }

    override fun onDestroy() {
        router = null
        view = null
    }

}