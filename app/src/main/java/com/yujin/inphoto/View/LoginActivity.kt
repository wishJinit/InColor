package com.yujin.inphoto.view

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.yujin.inphoto.Base.BaseActivity
import com.yujin.inphoto.R
import com.yujin.inphoto.Util.ConfirmUtil
import com.yujin.inphoto.Util.DLog
import com.yujin.inphoto.ViewModel.MemberViewModel
import com.yujin.inphoto.databinding.ActivityLoginBinding
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
                checkLogin(it)
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
    private fun checkLogin(btn:View){
        showProgressBar()
        disableButton(btn)

        val id = id_edit_text.text.toString()
        val pw = pw_edit_text.text.toString()
        viewModel.singIn(id, pw,
            {
                if (auto_sign_in_check_box.isChecked) {
                    DLog.d("AutoLogin")
                    viewModel.setAutoSignIn(this)
                }

                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                successSignIn()
            },
            {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            },
            {
                hideProgressBar()
                enableButton(btn)
            })
    }

    private fun successSignIn(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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
