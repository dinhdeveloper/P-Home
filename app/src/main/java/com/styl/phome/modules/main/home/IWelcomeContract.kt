package com.styl.phome.modules.main.home

import com.styl.phome.modules.base.IBaseContract

interface IWelcomeContract {
    interface IView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun onAddPHome()
    }

    interface IRouter {
        fun navigateToAddPHome()
    }
}