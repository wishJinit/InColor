package com.yujin.inphoto.view

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.yujin.inphoto.Base.BaseActivity
import com.yujin.inphoto.R
import com.yujin.inphoto.util.ConfirmUtil
import com.yujin.inphoto.ViewModel.MemberViewModel
import com.yujin.inphoto.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : BaseActivity<ActivitySignupBinding, MemberViewModel>() {
    override lateinit var viewModel: MemberViewModel
    override val layoutResourceId: Int
        get() = R.layout.activity_signup

    private var isCheckEmail = false

    override fun initSetting() {
        viewModel = ViewModelProviders.of(this)[MemberViewModel::class.java]
    }

    override fun setDataBinding() { }

    override fun afterDataBinding() {
        setEventListener()
    }

    private fun setEventListener(){
        sign_up_button.setOnClickListener {
            val name = name_edit_text.text.toString()
            val id = id_edit_text.text.toString()
            val pw = pw_edit_text.text.toString()
            val checkPw = check_pw_edit_text.text.toString()

            if(ConfirmUtil.isEmptyField(name, id, pw, checkPw)){
                Toast.makeText(this, "모든 항목 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!ConfirmUtil.isEmailValid(id)){
                Toast.makeText(this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
            } else if (!checkPassword()){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else if (!isCheckEmail){
                Toast.makeText(this, "이메일 중복확인을 해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                createUser(it)
            }
        }

        check_email_btn.setOnClickListener {
            val id = id_edit_text.text.toString()
            val resultTextView = check_email_result_text_view
            resultTextView.visibility = View.VISIBLE

            if (ConfirmUtil.isEmptyField(id)) {
                resultTextView.setText(R.string.notify_fill_out_email)
            } else if (!ConfirmUtil.isEmailValid(id)){
                resultTextView.setText(R.string.notify_check_email)
            } else {
                showProgressBar()
                disableButton(it)
                viewModel.checkEmail(id,
                    {
                        resultTextView.setText(R.string.notify_able_sign_up)
                        isCheckEmail = true
                    },
                    {
                        resultTextView.setText(R.string.notify_unable_sign_up)
                        isCheckEmail = false
                    },
                    {
                        resultTextView.setText(R.string.notify_check_fail)
                        isCheckEmail = false
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

    private fun checkPassword() = pw_edit_text.text.toString() == check_pw_edit_text.text.toString()

    private fun createUser(btn:View){
        showProgressBar()
        disableButton(btn)

        val name = name_edit_text.text.toString()
        val id = id_edit_text.text.toString()
        val pw = pw_edit_text.text.toString()

        viewModel.signUp(name, id, pw,
            {
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                finish()
            },
            {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
            },
            {
                hideProgressBar()
                enableButton(btn)
            })
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
