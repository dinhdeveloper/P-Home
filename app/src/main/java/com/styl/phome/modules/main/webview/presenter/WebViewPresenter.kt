package com.styl.phome.modules.main.webview.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.webview.IWebViewContact
import com.styl.phome.modules.main.webview.router.WebViewRouter

class WebViewPresenter  (private var view: IWebViewContact.IView?) : IWebViewContact.IPresenter {
    private var router: WebViewRouter? = WebViewRouter(view as? BaseFragment)
    override fun onDeviceFound() {
        router?.navigateDeviceFound()
    }

    override fun onDestroy() {
        router = null
        view = null
    }
}