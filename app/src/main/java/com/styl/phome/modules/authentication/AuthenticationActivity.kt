package com.styl.phome.modules.authentication

import android.os.Bundle
import com.styl.phome.R
import com.styl.phome.modules.authentication.signin.view.SignInFragment
import com.styl.phome.modules.base.BaseActivity

class AuthenticationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        initFragment()
    }

    private fun initFragment() {
        replaceFragment(
            fragmentManager = supportFragmentManager,
            containerId = R.id.container,
            fragment = SignInFragment.newInstance(),
            addBackStack = false
        )
    }
}