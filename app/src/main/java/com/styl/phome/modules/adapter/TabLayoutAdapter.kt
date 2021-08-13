package com.styl.phome.modules.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.styl.phome.modules.main.detailmode.view.DetailModeFragment
import com.styl.phome.modules.main.machinemode.view.MachineModeFragment

class TabLayoutAdapter(
    fragmentManager: FragmentManager,
    totalTabs: Int
) : FragmentPagerAdapter(fragmentManager) {

    private var totalTabs: Int? = totalTabs

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> return MachineModeFragment()
            else -> DetailModeFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs ?: 0
    }
}