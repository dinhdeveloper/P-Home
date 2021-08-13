package com.styl.phome.modules.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.styl.phome.R
import com.styl.phome.modules.base.BaseActivity
import com.styl.phome.modules.authentication.AuthenticationActivity
import com.styl.phome.modules.main.MainActivity
import com.styl.phome.modules.splash.ISplashContract
import com.styl.phome.modules.splash.presenter.SplashPresenter

class SplashActivity : BaseActivity(), ISplashContract.IView {
    private val delayTime = 1000L
    private var presenter: SplashPresenter? = SplashPresenter(this)
    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            handleNavigation()
        }, delayTime)
    }

    private fun handleNavigation() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
        presenter = null
    }
}