package com.styl.phome.modules.main.scanqr.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.scanqr.IScanContact
import com.styl.phome.modules.main.scanqr.router.ScanRouter

class ScanPresenter(private var view: IScanContact.IView?) : IScanContact.IPresenter {
    private var router: ScanRouter? = ScanRouter(view as? BaseFragment)
    override fun onDeviceFound() {
        router?.navigateDeviceFound()
    }

    override fun onDestroy() {
        router = null
        view = null
    }
}