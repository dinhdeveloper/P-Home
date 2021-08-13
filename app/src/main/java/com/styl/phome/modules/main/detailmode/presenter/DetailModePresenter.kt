package com.styl.phome.modules.main.detailmode.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.detailmode.IDetailMode
import com.styl.phome.modules.main.detailmode.router.DetailModeRouter
import com.styl.phome.modules.main.myhome.IMyHomeContact

class DetailModePresenter(private var view: IDetailMode.IView?) :
    IMyHomeContact.IPresenter {
    private var router: DetailModeRouter? = DetailModeRouter(view as? BaseFragment)

    override fun onDestroy() {
        router = null
        view = null
    }
}