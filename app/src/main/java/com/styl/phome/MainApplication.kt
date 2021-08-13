package com.styl.phome

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log

class MainApplication : Application(), Application.ActivityLifecycleCallbacks {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext(): Context? {
            return instance?.applicationContext
        }
    }

    private val TAG = "MainApplication"
    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }


    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            Log.d(TAG, "App enters foreground")
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d(TAG, "App Destroy")
        if (activityReferences == 0) {
            Log.d(TAG, "App Destroy")
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            Log.d(TAG, "App enters background")
        }
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
    }
}