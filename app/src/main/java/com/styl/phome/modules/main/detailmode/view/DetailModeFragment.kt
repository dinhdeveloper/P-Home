package com.styl.phome.modules.main.detailmode.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.detailmode.IDetailMode
import com.styl.phome.modules.main.detailmode.presenter.DetailModePresenter

class DetailModeFragment : BaseFragment(), View.OnClickListener, IDetailMode.IView {
    companion object {

        fun newInstance(): DetailModeFragment {
            return DetailModeFragment()
        }
    }

    private var presenter: DetailModePresenter? = DetailModePresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_detail_mode
    }

    override fun init() {

    }

    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.bt_connect -> {
//
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }


}