package com.styl.phome.modules.main.scanqr

import com.styl.phome.modules.base.IBaseContract

interface IScanContact {
    interface IView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun onDeviceFound()
    }

    interface IRouter {
        fun navigateDeviceFound()
    }
}