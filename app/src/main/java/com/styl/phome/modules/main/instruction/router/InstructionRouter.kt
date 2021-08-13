package com.styl.phome.modules.main.instruction.router

import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.instruction.IInstructionContract
import com.styl.phome.modules.main.scanqr.view.ScanFragment

class InstructionRouter(private var fragment: BaseFragment?) : IInstructionContract.IRouter {
    override fun navigateScan() {
        fragment?.let {
            it.replaceFragment(
                it.parentFragmentManager,
                R.id.container,
                ScanFragment.newInstance()
            )
        }
    }
}