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

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.styl.phome.R
import com.styl.phome.entities.BaseResponse

abstract class BaseFragment : Fragment(), IBaseContract.IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(getMenuLayout() != null)
    }

    abstract fun getLayoutResId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        init()
    }

    abstract fun init()

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    private fun initToolbar() {
        getToolBarId()?.let {
            view?.findViewById<Toolbar>(it)?.let { toolbar ->
                activity?.title = ""
                (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
                toolbar.setNavigationOnClickListener {
                    activity?.onBackPressed()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        getMenuLayout()?.let {
            inflater.inflate(it, menu)
        }
    }

    open fun getMenuLayout(): Int? = null

    open fun getToolBarId(): Int? = R.id.toolbar

    private fun showSystemDialog(
        title: String,
        content: String,
        positiveText: String,
        negativeText: String,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?
    ) {
        val ctx = activity ?: return
        val builder = AlertDialog.Builder(ctx)
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

    override fun showLoading(messageResId: Int?) {
        showProgressLoading(messageResId)
    }

    override fun dismissLoading() {
        hideProgressDialog()
    }

//    fun addFragmentDialog(
//        fragmentManager: FragmentManager?,
//        containerId: Int,
//        fragment: BaseFragment,
//        addBackStack: Boolean = true,
//        hasAnim: Boolean = true
//    ) {
//        fragmentManager?.let {
//            val ft = it.beginTransaction()
//            if (hasAnim) {
//                ft.setCustomAnimations(
//                    R.anim.zoom_out,
//                    R.anim.zoom_in,
//                    R.anim.zoom_out,
//                    R.anim.zoom_in
//                )
//            }
//            ft.replace(containerId, fragment)
//            if (addBackStack) {
//                ft.addToBackStack(fragment::class.java.canonicalName)
//            }
//            ft.commit()
//        }
//    }

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

    fun addFragment(
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
            ft.add(containerId, fragment)
            if (addBackStack) {
                ft.addToBackStack(fragment::class.java.canonicalName)
            }
            ft.commit()
        }
    }

    fun replaceFragmentWithoutExitAnim(
        fragmentManager: FragmentManager?,
        containerId: Int,
        fragment: BaseFragment
    ) {
        fragmentManager?.let {
            val ft = it.beginTransaction()
            ft.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.fade_out
            )
            ft.replace(containerId, fragment)
            ft.addToBackStack(fragment::class.java.canonicalName)
            ft.commit()
        }
    }

    fun showProgressLoading(messageResId: Int?) {
        (activity as? BaseActivity)?.showProgressLoading(messageResId)
    }

    fun hideProgressDialog() {
        (activity as? BaseActivity)?.hideProgressDialog()
    }

    fun popToRoot() {
        activity?.supportFragmentManager?.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    protected fun hideKeyboard() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = requireActivity().currentFocus
        if (view == null) {
            view = View(requireContext())
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun showKeyboard() {
        val imm: InputMethodManager? =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    override fun <T> showErrorMessage(response: BaseResponse<T>) {
        (activity as? BaseActivity)?.showErrorMessage(response)
    }

    override fun showErrorMessage(messageResId: Int) {
        (activity as? BaseActivity)?.showErrorMessage(messageResId)
    }

    fun showErrorMessage(message: String) {
        (activity as? BaseActivity)?.showErrorMessage(message)
    }
}