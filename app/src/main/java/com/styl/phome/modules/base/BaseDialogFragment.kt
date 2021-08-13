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
package com.styl.phome.modules.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.styl.phome.MainActivity
import com.styl.phome.R
import com.styl.phome.entities.BaseResponse

abstract class BaseDialogFragment : DialogFragment(), IBaseContract.IBaseView {

    var v: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        getLayoutResource()?.let {
            v = activity?.layoutInflater?.inflate(it, null)
            dialog?.setContentView(v!!)
        }
        isCancelable = false

        //clear background
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        this.initializeView(savedInstanceState)

        return dialog!!
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
    }

    override fun onStop() {
        super.onStop()
        this.dismissLoading()
    }

    override fun showLoading(messageResId: Int?) {
        (activity as? MainActivity)?.showLoading(messageResId)
    }

    override fun dismissLoading() {
        (activity as? MainActivity)?.dismissLoading()
    }

    override fun showErrorMessage(messageResId: Int) {
        (activity as? MainActivity)?.showErrorMessage(messageResId)
    }


    override fun <T> showErrorMessage(response: BaseResponse<T>) {
        (activity as? MainActivity)?.showErrorMessage(response)
    }

    abstract fun initializeView(savedInstanceState: Bundle?)
    abstract fun getLayoutResource(): Int
}