package com.styl.phome.modules.splash

import com.styl.phome.modules.base.IBaseContract

interface ISplashContract {
    interface IView : IBaseContract.IBaseView {
    }

    interface IPresenter : IBaseContract.IBasePresenter {
    }

    interface IRouter {
        fun navigateToExample()
    }

    interface IInteractor : IBaseContract.IBaseInteractor {
        fun exampleApi()
    }

    interface IExampleOutput : IBaseContract.IBaseInteractorOutput<String>
}