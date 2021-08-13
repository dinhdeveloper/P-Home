package com.styl.phome.modules.main.webview.router

import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.scanqr.IScanContact
import com.styl.phome.modules.main.setupwifi.view.SetUpWifiFragment
import com.styl.phome.modules.main.webview.IWebViewContact
import com.styl.phome.modules.main.webview.view.WebViewFragment

class WebViewRouter (private var fragment: BaseFragment?) : IWebViewContact.IRouter {

    override fun navigateDeviceFound() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                WebViewFragment.newInstance()
            )
        }
    }
}