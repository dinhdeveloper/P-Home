package com.styl.phome.modules.main.instruction.presenter

import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.instruction.IInstructionContract
import com.styl.phome.modules.main.instruction.router.InstructionRouter

class InstructionPresenter(private var view: IInstructionContract.IView?) :
    IInstructionContract.IPresenter {
    private var router: InstructionRouter? = InstructionRouter(view as? BaseFragment)
    override fun openScan() {
        router?.navigateScan()
    }

    override fun onDestroy() {
        router = null
        view = null
    }
}