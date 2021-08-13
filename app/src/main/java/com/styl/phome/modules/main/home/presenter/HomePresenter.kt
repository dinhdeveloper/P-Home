package com.styl.phome.modules.main.home.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.home.IWelcomeContract
import com.styl.phome.modules.main.home.router.HomeRouter

class HomePresenter(private var view: IWelcomeContract.IView?) : IWelcomeContract.IPresenter {
    private var router: HomeRouter? = HomeRouter(view as? BaseFragment)

    override fun onAddPHome() {
        router?.navigateToAddPHome()
    }

    override fun onDestroy() {
        router = null
        view = null
    }
}