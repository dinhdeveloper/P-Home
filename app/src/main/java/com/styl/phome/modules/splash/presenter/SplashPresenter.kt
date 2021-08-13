package com.styl.phome.modules.splash.presenter

import com.styl.phome.entities.BaseResponse
import com.styl.phome.modules.splash.ISplashContract
import com.styl.phome.modules.splash.interactor.SplashInteractor
import com.styl.phome.modules.splash.router.SplashRouter

class SplashPresenter(private var view: ISplashContract.IView?) : ISplashContract.IPresenter {
    private var router: SplashRouter? = SplashRouter()
    private var interactor: SplashInteractor? = SplashInteractor()

    init {
        interactor?.output = object : ISplashContract.IExampleOutput {
            override fun onSuccess(data: String?) {
            }

            override fun onError(data: BaseResponse<String>) {

            }
        }
    }

    override fun onDestroy() {
        router = null
        view = null
    }
}