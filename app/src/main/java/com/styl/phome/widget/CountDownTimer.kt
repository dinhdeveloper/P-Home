/*
 * (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved
 * This source code and any compilation or derivative thereof is the sole
 * property of STYL Solutions Pte. Ltd. and is provided pursuant to a
 * Software License Agreement.  This code is the proprietary information of
 * STYL Solutions Pte. Ltd. and is confidential in nature. Its use and
 * dissemination by any party other than STYL Solutions Pte. Ltd. is strictly
 * limited by the confidential information provisions of the Agreement
 * referenced above.
 */

package com.styl.phome.widget

import android.os.Handler
import android.os.Looper

class CountDownTimer(duration: Int, private val listener: CountDownListener) {
    private val intervalAsMilliseconds = 1000L
    private var time: Int = duration
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = Runnable {
        time--
        if (time != 0) {
            listener.onTick(time)
            start()
        } else {
            //reset time
            time = duration
            listener.onFinishCountDown()
        }
    }

    fun start() {
        handler.postDelayed(runnable, intervalAsMilliseconds)
    }

    fun clear() {
        handler.removeCallbacks(runnable)
    }

    fun getTime() : Int {
        return time
    }

    interface CountDownListener {
        fun onTick(value: Int)
        fun onFinishCountDown()
    }
}