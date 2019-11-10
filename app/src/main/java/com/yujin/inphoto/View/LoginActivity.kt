package com.yujin.inphoto.View

import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.yujin.inphoto.Base.BaseActivity
import com.yujin.inphoto.R
import com.yujin.inphoto.ViewModel.MemberViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern


class LoginActivity : BaseActivity<ViewDataBinding, MemberViewModel>() {
    override var baseViewModel = MemberViewModel()
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun initSetting() {}

    override fun setDataBinding() {}

    override fun afterDataBinding() {
        setEventListener()
    }

    // Event Listener 설정
    private fun setEventListener(){
        login_button.setOnClickListener {
            if (!checkInputField()){
                Toast.makeText(this, "ID/PW를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if(!isEmailValid(id_edit_text.text.toString())){
                Toast.makeText(this, "유효한 이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                checkLogin()
            }
        }
    }

    // id, pw 필드 입력 체크
    private fun checkInputField() = id_edit_text.text.toString().isNotEmpty() && pw_edit_text.text.toString().isNotEmpty()

    // 이메일 유효성 검사
    private fun isEmailValid(email: String): Boolean {
        val regExpn = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

        val pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }

    // 계정 체크
    private fun checkLogin(){
        showProgressBar()
        hideProgressBar()
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }
}
