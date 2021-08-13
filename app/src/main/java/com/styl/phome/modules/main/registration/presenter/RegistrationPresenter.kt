package com.styl.phome.modules.main.registration.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.registration.IRegistrationContact
import com.styl.phome.modules.main.registration.router.RegistrationRouter

class RegistrationPresenter(private var view: IRegistrationContact.IView?) :
    IRegistrationContact.IPresenter {
    private var router: RegistrationRouter? = RegistrationRouter(view as? BaseFragment)

    override fun onDestroy() {
        router = null
        view = null
    }

    override fun wifiSetup() {
        router?.navigatePhoneFound()
    }
}