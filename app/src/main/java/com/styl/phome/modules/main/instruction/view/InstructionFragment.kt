package com.styl.phome.modules.main.instruction.view

import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.modules.main.instruction.IInstructionContract
import com.styl.phome.modules.main.instruction.presenter.InstructionPresenter
import kotlinx.android.synthetic.main.fragment_instruction.*

class InstructionFragment : BaseFragment(), View.OnClickListener, IInstructionContract.IView {

    companion object {

        fun newInstance(): InstructionFragment {
            return InstructionFragment()
        }
    }

    private var presenter: InstructionPresenter? = InstructionPresenter(this)

    override fun getLayoutResId(): Int {
        return R.layout.fragment_instruction
    }

    override fun init() {
        bt_start_scan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_start_scan -> {
                presenter?.openScan()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}