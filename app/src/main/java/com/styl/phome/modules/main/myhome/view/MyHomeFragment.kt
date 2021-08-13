package com.styl.phome.modules.main.myhome.view

import android.annotation.SuppressLint
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.styl.phome.R
import com.styl.phome.modules.adapter.TabLayoutAdapter
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.myhome.IMyHomeContact
import com.styl.phome.modules.main.myhome.presenter.MyHomePresenter
import kotlinx.android.synthetic.main.fragment_my_home.*

class MyHomeFragment : BaseFragment(), View.OnClickListener, IMyHomeContact.IView {
    companion object {

        fun newInstance(): MyHomeFragment {
            return MyHomeFragment()
        }
    }

    private var presenter: MyHomePresenter? = MyHomePresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_my_home
    }

    override fun init() {
//        bt_connect.setOnClickListener(this)
        initTabLayout()
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

    private fun initTabLayout() {
        val adapterTab =
            this.activity?.supportFragmentManager?.let { TabLayoutAdapter(
                it, tab_layout.tabCount) }
        view_pager.adapter = adapterTab
        view_pager
            .addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.cancelPendingInputEvents()
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                view_pager.currentItem = p0?.position ?: 0
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                view_pager.currentItem = p0?.position ?: 0
            }
        })

    }
}