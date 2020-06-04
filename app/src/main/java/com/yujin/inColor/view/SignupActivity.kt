package com.yujin.inColor.view

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yujin.inColor.Base.BaseActivity
import com.yujin.inColor.R
import com.yujin.inColor.util.ConfirmUtil
import com.yujin.inColor.ViewModel.MemberViewModel
import com.yujin.inColor.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : BaseActivity<ActivitySignupBinding, MemberViewModel>() {
    override lateinit var viewModel: MemberViewModel
    override val layoutResourceId: Int
        get() = R.layout.activity_signup

    private var isCheckEmail = false

    override fun initSetting() {
        viewModel = ViewModelProviders.of(this)[MemberViewModel::class.java]
        viewModel.resetResult()
    }

    override fun setDataBinding() { 
        viewModel.signUpResult.observe(this, Observer { result ->
            enableSignUpButton()
            hideProgressBar()

            if (result == 1) {
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                finish()
            } else if (result == 0) {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.checkEmailResult.observe(this, Observer { result ->
            hideProgressBar()
            enableCheckEmailButton()
            if (result == 1) {
                check_email_result_text_view.setText(R.string.notify_unable_sign_up)
                isCheckEmail = false
            } else if (result == 0) {
                check_email_result_text_view.setText(R.string.notify_able_sign_up)
                isCheckEmail = true
            }
        })
    }

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
                createUser()
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
                disableCheckEmailButton()

                viewModel.checkEmail(id)
            }
        }

        back_btn.setOnClickListener {
            finish()
        }
    }

    private fun checkPassword() = pw_edit_text.text.toString() == check_pw_edit_text.text.toString()

    private fun createUser(){
        showProgressBar()
        disableSignUpButton()

        val name = name_edit_text.text.toString()
        val id = id_edit_text.text.toString()
        val pw = pw_edit_text.text.toString()

        viewModel.signUp(name, id, pw)
    }

    private fun enableCheckEmailButton(){
        check_email_btn.isEnabled = true
    }

    private fun disableCheckEmailButton(){
        check_email_btn.isEnabled = false
    }

    private fun enableSignUpButton(){
        sign_up_button.isEnabled = true
    }

    private fun disableSignUpButton(){
        sign_up_button.isEnabled = false
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }

}
