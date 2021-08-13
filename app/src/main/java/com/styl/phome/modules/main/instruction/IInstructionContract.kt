package com.styl.phome.modules.main.instruction

import com.styl.phome.modules.base.IBaseContract

interface IInstructionContract {
    interface IView {

    }

    interface IPresenter : IBaseContract.IBasePresenter {
        fun openScan()
    }

    interface IRouter {
        fun navigateScan()
    }
}