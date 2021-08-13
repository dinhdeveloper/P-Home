package com.styl.phome.modules.main.scanqr.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.scanqr.IScanContact
import com.styl.phome.modules.main.scanqr.presenter.ScanPresenter
import kotlinx.android.synthetic.main.fragment_scan.*

class ScanFragment : BaseFragment(), View.OnClickListener, IScanContact.IView {
    companion object {

        fun newInstance(): ScanFragment {
            return ScanFragment()
        }
    }

    private var presenter: ScanPresenter? = ScanPresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_scan
    }

    override fun init() {
        bt_go.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_go -> {
                presenter?.onDeviceFound()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

}