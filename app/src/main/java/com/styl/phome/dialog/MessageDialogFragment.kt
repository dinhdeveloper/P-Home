package com.styl.phome.dialog

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_message_dialog.*


class MessageDialogFragment : BaseFragment {
    private var title: String? = null
    private var content: String? = null
    private var negativeContent: String? = null
    private var positiveContent: String? = null

    companion object {
        fun newInstance(): MessageDialogFragment {
            return MessageDialogFragment()
        }
    }

    constructor()

    constructor(
        title: String?,
        content: String?,
        negativeContent: String?,
        positiveContent: String?
    ) {
        this.title = title
        this.content = content
        this.negativeContent = negativeContent
        this.positiveContent = positiveContent
    }

    private fun initUI() {
        tv_title.text = title
        bt_skip.text = negativeContent
        bt_save.text = positiveContent
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_message_dialog
    }

    override fun init() {
        initUI()
        bt_skip.setOnClickListener {
            fm_dialog.visibility = View.GONE
        }
        bt_save.setOnClickListener {
            fm_dialog.visibility = View.GONE
        }
    }

}