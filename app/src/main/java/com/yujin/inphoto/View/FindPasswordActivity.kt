package com.yujin.inphoto.view

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.yujin.inphoto.Base.BaseActivity
import com.yujin.inphoto.R
import com.yujin.inphoto.Util.ConfirmUtil
import com.yujin.inphoto.ViewModel.MemberViewModel
import com.yujin.inphoto.databinding.ActivityFindPasswordBinding
import kotlinx.android.synthetic.main.activity_find_password.*

class FindPasswordActivity : BaseActivity<ActivityFindPasswordBinding, MemberViewModel>() {
    override lateinit var viewModel: MemberViewModel
    override val layoutResourceId: Int
        get() = R.layout.activity_find_password

    override fun initSetting() {
        viewModel = ViewModelProviders.of(this)[MemberViewModel::class.java]
    }

    override fun setDataBinding() { }

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
                disableButton(it)

                val sendMail = {
                    viewModel.sendPasswordResetEmail(
                        id_edit_text.text.toString(),
                        {
                            Toast.makeText(this, "메일전송 성공", Toast.LENGTH_SHORT).show()
                            finish()
                        },
                        {
                            Toast.makeText(this, "메일전송 실패", Toast.LENGTH_SHORT).show()
                        },
                        {
                            hideProgressBar()
                            enableButton(it)
                        })
                }

                viewModel.checkEmail(id_edit_text.text.toString(),
                    {
                        Toast.makeText(this, "등록된 회원이 아닙니다.", Toast.LENGTH_SHORT).show()
                    },
                    sendMail,
                    {
                        Toast.makeText(this, "메일전송 실패", Toast.LENGTH_SHORT).show()
                    },
                    {
                        hideProgressBar()
                        enableButton(it)
                    })
            }
        }

        back_btn.setOnClickListener {
            finish()
        }
    }

    private fun enableButton(btn:View){
        btn.isEnabled = true
    }

    private fun disableButton(btn:View){
        btn.isEnabled = false
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }
}
