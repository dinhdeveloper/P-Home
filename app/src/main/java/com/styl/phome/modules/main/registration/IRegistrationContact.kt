package com.styl.phome.modules.main.registration

import com.styl.phome.modules.base.IBaseContract

interface IRegistrationContact {
    interface IView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun wifiSetup()
    }

    interface IRouter {
        fun navigatePhoneFound()
    }
}