package com.styl.phome.modules.splash.interactor

import com.styl.phome.modules.base.BaseInteractor
import com.styl.phome.modules.splash.ISplashContract
import com.styl.phome.services.AuthenticateApiServices
import com.styl.phome.services.ServiceGenerator

class SplashInteractor : ISplashContract.IInteractor, BaseInteractor() {

    private val services = ServiceGenerator.createService(AuthenticateApiServices::class.java)

    var output: ISplashContract.IExampleOutput? = null

    override fun exampleApi() {

    }

//    override fun checkAppUpdate(version: String) {
//        ApiClient.getInstance()?.request(
//            services.checkAppVersion(version), object : IRequestCallBack<AppUpdateResponse> {
//                override fun onRequestSuccess(response: Response<AppUpdateResponse>) {
//                    onResponse(response, checkUpdateOutput)
//                }
//
//                override fun onRequestFailed(t: Throwable) {
//                    onError(t, checkUpdateOutput)
//                }
//
//            }, false
//        )
//    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }
}
