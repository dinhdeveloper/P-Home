package com.styl.phome.modules.main.webview

import com.styl.phome.modules.base.IBaseContract

interface IWebViewContact {
    interface IView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun onDeviceFound()
    }

    interface IRouter {
        fun navigateDeviceFound()
    }
}