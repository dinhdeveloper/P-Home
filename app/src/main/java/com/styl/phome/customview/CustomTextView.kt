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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.styl.phome.R

class CustomTextView(ctx: Context, attributeSet: AttributeSet) : AppCompatTextView(ctx, attributeSet) {

    init {
        setCustomFont(ctx, attributeSet)
    }

    @SuppressLint("CustomViewStyleable")
    private fun setCustomFont(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView)
        val customFont = typedArray.getString(R.styleable.MyCustomView_customFont)
        try {
            val tf = Typeface.createFromAsset(context.assets, customFont)
            typeface = tf
        } catch (e: Exception) {
            e.printStackTrace()
        }
        typedArray.recycle()
    }
}