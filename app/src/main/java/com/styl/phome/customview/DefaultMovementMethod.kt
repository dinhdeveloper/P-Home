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
package com.styl.phome.customview

import android.text.Selection
import android.text.Spannable
import android.text.method.MovementMethod
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.TextView

internal class DefaultMovementMethod private constructor() : MovementMethod {
    override fun initialize(widget: TextView, text: Spannable) {
        Selection.setSelection(text, 0)
    }

    override fun onKeyDown(
        widget: TextView,
        text: Spannable,
        keyCode: Int,
        event: KeyEvent
    ): Boolean {
        return false
    }

    override fun onKeyUp(
        widget: TextView,
        text: Spannable,
        keyCode: Int,
        event: KeyEvent
    ): Boolean {
        return false
    }

    override fun onKeyOther(
        view: TextView,
        text: Spannable,
        event: KeyEvent
    ): Boolean {
        return false
    }

    override fun onTakeFocus(
        widget: TextView,
        text: Spannable,
        direction: Int
    ) {
    }

    override fun onTrackballEvent(
        widget: TextView,
        text: Spannable,
        event: MotionEvent
    ): Boolean {
        return false
    }

    override fun onTouchEvent(
        widget: TextView,
        text: Spannable,
        event: MotionEvent
    ): Boolean {
        return false
    }

    override fun onGenericMotionEvent(
        widget: TextView,
        text: Spannable,
        event: MotionEvent
    ): Boolean {
        return false
    }

    override fun canSelectArbitrarily(): Boolean {
        return false
    }

    companion object {
        private var sInstance: DefaultMovementMethod? = null
        val instance: MovementMethod?
            get() {
                if (sInstance == null) {
                    sInstance = DefaultMovementMethod()
                }
                return sInstance
            }
    }
}
