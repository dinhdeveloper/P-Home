package com.styl.phome.modules.main.setupwifi

import com.styl.phome.modules.base.IBaseContract

interface ISetUpWifiContact {
    interface IView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun onRegistration()
        fun onMyHome()
    }

    interface IRouter {
        fun navigateRegistration()
        fun navigateMyHome()
    }
}