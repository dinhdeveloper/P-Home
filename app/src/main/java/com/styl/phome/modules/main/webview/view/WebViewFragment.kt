package com.styl.phome.modules.main.webview.view

import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.webview.IWebViewContact
import com.styl.phome.modules.main.webview.presenter.WebViewPresenter
import kotlinx.android.synthetic.main.fragment_scan.*
import kotlinx.android.synthetic.main.fragment_webview.*

class WebViewFragment : BaseFragment(), View.OnClickListener, IWebViewContact.IView {

   // private lateinit var webView: WebView

    companion object {

        fun newInstance(): WebViewFragment {
            return WebViewFragment()
        }
    }

    private var presenter: WebViewPresenter? = WebViewPresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_webview
    }

    override fun init() {
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?): Boolean {
                return false
            }
        }
        webView.loadUrl("https://www.google.com")
    }

    override fun onClick(v: View?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

}