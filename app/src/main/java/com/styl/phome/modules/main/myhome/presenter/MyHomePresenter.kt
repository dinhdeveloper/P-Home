package com.styl.phome.modules.main.myhome.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.myhome.IMyHomeContact
import com.styl.phome.modules.main.myhome.router.MyHomeRouter

class MyHomePresenter(private var view: IMyHomeContact.IView?) :
    IMyHomeContact.IPresenter {
    private var router: MyHomeRouter? = MyHomeRouter(view as? BaseFragment)

    override fun onDestroy() {
        router = null
        view = null
    }
}