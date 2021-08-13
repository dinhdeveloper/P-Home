package com.styl.phome.modules.main.registration.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.registration.IRegistrationContact
import com.styl.phome.modules.main.registration.presenter.RegistrationPresenter
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : BaseFragment(), View.OnClickListener, IRegistrationContact.IView {

    companion object {
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    private var presenter: RegistrationPresenter? = RegistrationPresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_registration
    }

    override fun init() {
//        bt_edit.setOnClickListener(this)
        bt_wifi_set_up.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.bt_edit -> {
//                this.let {
//                    it.addFragment(
//                        it.parentFragmentManager,
//                        R.id.container,
//                        MessageDialogFragment(
//                            getString(R.string.p_home_name),
//                            null,
//                            getString(R.string.skip),
//                            getString(R.string.save)
//                        ),
//                        hasAnim = false
//                    )
//                }
//            }
            R.id.bt_wifi_set_up -> {
                presenter?.wifiSetup()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

}