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

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.styl.phome.MainActivity
import com.styl.phome.R
import com.styl.phome.entities.BaseResponse
import com.styl.phome.utils.MySharedPref

abstract class BaseActivity : AppCompatActivity(), IBaseContract.IBaseView {
    private var progressDialog: AlertDialog? = null
    private var alertDialog: AlertDialog? = null

    fun isAnyDialogIsShowing(): Boolean {
        return alertDialog?.isShowing == true
    }

    override fun showLoading(messageResId: Int?) {
        showProgressLoading(messageResId)
    }

    override fun dismissLoading() {
        hideProgressDialog()
    }

    override fun <T> showErrorMessage(response: BaseResponse<T>) {
        alertDialog?.dismiss()
        if (response.errorCode == 401 && this is MainActivity) {
            showErrorMessage(message = getString(R.string.app_name),
                listener = DialogInterface.OnClickListener { _, _ ->
                    clearUserData()
                    handleUnAuthenticateNavigate()
                })
        } else {
            showErrorMessage(response.errorMessage ?: "")
        }
    }

    override fun showErrorMessage(messageResId: Int) {
        showErrorMessage(getString(messageResId))
    }

    fun showErrorMessage(
        title: String? = null,
        message: String,
        listener: DialogInterface.OnClickListener?
    ) {
        dismissCurrentDialog()
        val builder =
            AlertDialog.Builder(this)
        alertDialog = builder.create()
        alertDialog?.setCancelable(false)
        if (title != null) {
            alertDialog?.setTitle(title)
        }
        alertDialog?.setMessage(message)
        alertDialog?.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), listener)
        alertDialog?.show()
    }

    fun showErrorMessage(
        message: String
    ) {
        showErrorMessage(message = message, listener = null)
    }

    private fun dismissCurrentDialog() {
        if (alertDialog?.isShowing == true) {
            alertDialog?.dismiss()
        }
    }


    private fun handleUnAuthenticateNavigate() {
//         val intent = Intent(this, LoginActivity::class.java)
//         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Removes other Activities from stack
//         startActivity(intent)
    }

    private fun clearUserData() {
        MySharedPref.clearUserCache(this)
    }

    fun getCurrentFragment(containerID: Int): Fragment? {
        return supportFragmentManager.findFragmentById(containerID)
    }

    fun replaceFragment(
        fragmentManager: FragmentManager?,
        containerId: Int,
        fragment: BaseFragment,
        addBackStack: Boolean = true,
        hasAnim: Boolean = true
    ) {
        fragmentManager?.let {
            val ft = it.beginTransaction()
            if (hasAnim) {
                ft.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
            }
            ft.replace(containerId, fragment)
            if (addBackStack) {
                ft.addToBackStack(fragment::class.java.canonicalName)
            }
            ft.commit()
        }
    }

    fun showSystemDialog(
        title: String?,
        content: String?,
        positiveText: String?,
        negativeText: String?,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?
    ) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(content)
            .setPositiveButton(positiveText, positiveListener)
            .setNegativeButton(negativeText, negativeListener)
            .setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

    fun showSystemDialog(
        titleRes: Int?,
        contentRes: Int,
        positiveRes: Int,
        negativeRes: Int,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?
    ) {
        val title = if (titleRes != null) {
            getString(titleRes)
        } else {
            ""
        }
        showSystemDialog(
            title,
            getString(contentRes),
            getString(positiveRes),
            getString(negativeRes),
            positiveListener,
            negativeListener
        )
    }

    fun showSystemDialog(
        titleRes: Int?,
        content: String,
        buttonTextRes: Int,
        listener: DialogInterface.OnClickListener?
    ) {
        val title = if (titleRes != null) {
            getString(titleRes)
        } else {
            ""
        }
        showSystemDialog(
            title,
            content,
            getString(buttonTextRes),
            null,
            listener,
            null
        )
    }

    fun showProgressLoading(messageResId: Int?) {
        val llContainer = LinearLayout(this)
        llContainer.orientation = LinearLayout.HORIZONTAL
        llContainer.setPadding(30, 50, 30, 50)
        llContainer.gravity = Gravity.START
        val llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.START
        llContainer.layoutParams = llParam

        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, 30, 0)
        progressBar.layoutParams = llParam
        llParam.gravity = Gravity.CENTER
        llContainer.addView(progressBar)
        val tvText = TextView(this)
        messageResId?.let {
            tvText.text = this.getString(messageResId)
        }
        tvText.setTextColor(ContextCompat.getColor(this, R.color.text_color))
        tvText.textSize = 14f
        tvText.layoutParams = llParam
        llContainer.addView(tvText)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(llContainer)
        if (progressDialog == null) {
            progressDialog = builder.create()
        }
        progressDialog?.show()
        progressDialog?.window?.let {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(it.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            it.attributes = layoutParams
        }
    }

    fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    fun popToRoot() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}