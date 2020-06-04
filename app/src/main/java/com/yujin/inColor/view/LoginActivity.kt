package com.yujin.inColor.view

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yujin.inColor.Base.BaseActivity
import com.yujin.inColor.R
import com.yujin.inColor.util.ConfirmUtil
import com.yujin.inColor.util.DLog
import com.yujin.inColor.ViewModel.MemberViewModel
import com.yujin.inColor.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity<ActivityLoginBinding, MemberViewModel>() {
    override lateinit var viewModel: MemberViewModel
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun initSetting() {
        viewModel = ViewModelProviders.of(this)[MemberViewModel::class.java]
        viewModel.autoSignIn(this) {
            successSignIn()
        }
    }

    override fun setDataBinding() {
        viewModel.userVO.observe(this, Observer {  user ->
            hideProgressBar()
            enableLoginButton()

            user?.let {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                successSignIn()
            } ?: run {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

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
                checkLogin()
            }
        }

        // 회원가입 Activity 이동
        create_user_tv.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        // 비밀번호 찾기 Activity 이동
        find_password_tv.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }

        auto_sign_in_layout.setOnClickListener {
            auto_sign_in_check_box.isChecked = !auto_sign_in_check_box.isChecked
        }
    }

    // 로그인
    private fun checkLogin(){
        showProgressBar()
        disableLoginButton()

        val id = id_edit_text.text.toString()
        val pw = pw_edit_text.text.toString()
        viewModel.singIn(id, pw)
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
