package com.yujin.inColor.view

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.yujin.inColor.base.BaseActivity
import com.yujin.inColor.R
import com.yujin.inColor.util.ConfirmUtil
import com.yujin.inColor.util.DLog
import com.yujin.inColor.viewModel.MemberViewModel
import com.yujin.inColor.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity<ActivityLoginBinding, MemberViewModel>() {
    override val viewModel by viewModel<MemberViewModel>()
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun initSetting() {
        viewModel.autoSignIn(this) {
            successSignIn()
        }
    }

    override fun setDataBinding() {}

    override fun afterDataBinding() {
        setEventListener()
    }

    // Event Listener 설정
    private fun setEventListener(){
        login_button.setOnClickListener {
            val id = id_edit_text.text.toString()
            val pw = pw_edit_text.text.toString()

            if (ConfirmUtil.isEmptyField(id, pw)){
                Toast.makeText(this, "ID/PW를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if(!ConfirmUtil.isEmailValid(id)){
                Toast.makeText(this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
            } else {
                checkLogin({
                    hideProgressBar()
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    successSignIn()
                }, {
                    hideProgressBar()
                    enableLoginButton()
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                })
            }
        }

        create_user_tv.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        find_password_tv.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }

        auto_sign_in_layout.setOnClickListener {
            auto_sign_in_check_box.isChecked = !auto_sign_in_check_box.isChecked
        }
    }

    // 로그인
    private fun checkLogin(success: () -> Unit, fail: () -> Unit){
        showProgressBar()
        disableLoginButton()

        val id = id_edit_text.text.toString()
        val pw = pw_edit_text.text.toString()
        viewModel.singIn(id, pw, success, fail)
    }

    private fun successSignIn(){
        if (auto_sign_in_check_box.isChecked) {
            DLog.d("AutoLogin")
            viewModel.setAutoSignIn(this)
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun enableLoginButton(){
        login_button.isEnabled = true
    }

    private fun disableLoginButton(){
        login_button.isEnabled = false
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }
}
