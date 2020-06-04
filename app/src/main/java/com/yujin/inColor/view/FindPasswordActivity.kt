package com.yujin.inColor.view

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yujin.inColor.Base.BaseActivity
import com.yujin.inColor.R
import com.yujin.inColor.util.ConfirmUtil
import com.yujin.inColor.ViewModel.MemberViewModel
import com.yujin.inColor.databinding.ActivityFindPasswordBinding
import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : BaseActivity<ActivityFindPasswordBinding, MemberViewModel>() {
    override lateinit var viewModel: MemberViewModel
    override val layoutResourceId: Int
        get() = R.layout.activity_find_password

    override fun initSetting() {
        viewModel = ViewModelProviders.of(this)[MemberViewModel::class.java]
    }

    override fun setDataBinding() {
        viewModel.sendEmailResult.observe(this, Observer { result ->
            hideProgressBar()
            enableSendMailButton()
            if(result == 1){
                Toast.makeText(this, "메일전송 성공", Toast.LENGTH_SHORT).show()
                finish()
            } else if (result == 0) {
                Toast.makeText(this, "메일전송 실패", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.checkEmailResult.observe(this, Observer { result ->
            hideProgressBar()
            enableSendMailButton()
            if (result == 1) {
                viewModel.sendPasswordResetEmail(id_edit_text.text.toString())
            } else if (result == 0) {
                Toast.makeText(this, "등록된 회원이 아니거나 메일 전송 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun afterDataBinding() {
        setEventListener()
    }

    private fun setEventListener() {
        send_mail_btn.setOnClickListener {
            val id = id_edit_text.text.toString()

            if (ConfirmUtil.isEmptyField(id)) {
                Toast.makeText(this, getString(R.string.notify_fill_out_email), Toast.LENGTH_SHORT).show()
            } else if (!ConfirmUtil.isEmailValid(id)){
                Toast.makeText(this, getString(R.string.notify_check_email), Toast.LENGTH_SHORT).show()
            } else {
                showProgressBar()
                disableSendMailButton()
                viewModel.checkEmail(id_edit_text.text.toString())
            }
        }

        back_btn.setOnClickListener {
            finish()
        }
    }

    private fun enableSendMailButton(){
        send_mail_btn.isEnabled = true
    }

    private fun disableSendMailButton(){
        send_mail_btn.isEnabled = false
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }
}
