package com.styl.phome.modules.main

import android.os.Bundle
import com.styl.phome.R
import com.styl.phome.modules.base.BaseActivity
import com.styl.phome.modules.main.home.view.HomeFragment
import com.styl.phome.modules.main.webview.view.WebViewFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        initFragment()
    }

    private fun initFragment() {
        replaceFragment(
            fragmentManager = supportFragmentManager,
            containerId = R.id.container,
            fragment = WebViewFragment.newInstance(),
            addBackStack = false
        )
    }
}