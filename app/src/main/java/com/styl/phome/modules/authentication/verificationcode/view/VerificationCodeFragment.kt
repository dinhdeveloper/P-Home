package com.styl.phome.modules.authentication.verificationcode.view

import android.os.Bundle
import android.view.View
import com.styl.phome.R
import com.styl.phome.modules.authentication.verificationcode.IVerificationCodeContract
import com.styl.phome.modules.authentication.verificationcode.presenter.VerificationCodePresenter
import com.styl.phome.modules.base.BaseFragment
import com.styl.phome.widget.CountDownTimer
import kotlinx.android.synthetic.main.verification_code_fragment.*

class VerificationCodeFragment : BaseFragment(), View.OnClickListener,
    CountDownTimer.CountDownListener, IVerificationCodeContract.IView {

    companion object {
        const val RESEND_TIME = 60//second
        private const val ARG_VERIFY_TYPE = "args.ARG_VERIFY_TYPE"
        const val VERIFY_FORGOT_PASSWORD = "VERIFY_FORGOT_PASSWORD"
        const val VERIFY_SIGN_UP = "VERIFY_SIGN_UP"

        fun newInstance(verifyType: String): VerificationCodeFragment {
            val args = Bundle()
            args.putString(ARG_VERIFY_TYPE, verifyType)
            val f = VerificationCodeFragment()
            f.arguments = args
            return f
        }
    }

    private var presenter: VerificationCodePresenter? = VerificationCodePresenter(this)
    private var countDownTimer: CountDownTimer? = null
    private var verifyType: String? = null

    override fun getLayoutResId(): Int {
        return R.layout.verification_code_fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            verifyType = it.getString(ARG_VERIFY_TYPE)
        }
    }

    override fun init() {
        setCountDownText(RESEND_TIME)
        tvResend?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        otpCode?.requestFocus()
        showKeyboard()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvResend -> {
                startCountDown()
            }
            R.id.btnSubmit -> {
                when (verifyType) {
                    VERIFY_SIGN_UP -> {
                        //todo handle api
                        popToRoot()
                    }
                    VERIFY_FORGOT_PASSWORD -> {
                        presenter?.onOpenNewPassword()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun startCountDown() {
        if (countDownTimer == null) {
            countDownTimer = CountDownTimer(RESEND_TIME, this)
        }
        tvResend?.isEnabled = false
        tvCountDown?.visibility = View.VISIBLE
        countDownTimer?.start()
    }

    private fun setCountDownText(value: Int) {
        tvCountDown?.text =
            String.format(getString(R.string.verify_count_down_format, value))
    }

    override fun onTick(value: Int) {
        setCountDownText(value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.clear()
    }

    override fun onFinishCountDown() {
        setCountDownText(RESEND_TIME)
        tvCountDown?.visibility = View.INVISIBLE
        tvResend?.isEnabled = true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}