package com.yujin.inphoto.View

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.yujin.inphoto.Base.BaseActivity
import com.yujin.inphoto.R
import com.yujin.inphoto.Util.ConfirmUtil
import com.yujin.inphoto.ViewModel.MemberViewModel
import com.yujin.inphoto.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity<ActivityLoginBinding, MemberViewModel>() {
    override lateinit var viewModel: MemberViewModel
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun initSetting() {
        viewModel = ViewModelProviders.of(this)[MemberViewModel::class.java]
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
                checkLogin()
            }
        }


    }

    // 로그인
    private fun checkLogin(){
        showProgressBar()
        val id = id_edit_text.text.toString()
        val pw = pw_edit_text.text.toString()
        viewModel.singIn(id, pw,
            {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            },
            {
                hideProgressBar()
            })
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }
}
