package com.yujin.inColor.view

import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.yujin.inColor.base.BaseActivity
import com.yujin.inColor.R
import com.yujin.inColor.util.ConfirmUtil
import com.yujin.inColor.viewModel.MemberViewModel
import com.yujin.inColor.databinding.ActivitySignupBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function5
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_signup.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity : BaseActivity<ActivitySignupBinding, MemberViewModel>() {
    override val viewModel by viewModel<MemberViewModel>()
    override val layoutResourceId: Int
        get() = R.layout.activity_signup

    override fun initSetting() {}
    override fun setDataBinding() {}

    override fun afterDataBinding() {
        setEventListener()
    }

    private fun setEventListener(){
        val nameCheckObservable = name_edit_text.textChanges()
            .map { it.isNotEmpty() }
        val idCheckObservable = id_edit_text.textChanges()
            .map { it.isNotEmpty() && ConfirmUtil.isEmailValid(it.toString()) }
        val pwCheckObservable = pw_edit_text.textChanges()
            .map { it.isNotEmpty() && it.length >= 6 }
        val checkPwCheckObservable = check_pw_edit_text.textChanges()
            .map { it.isNotEmpty() && checkPassword() }
        val checkEmailObservable = PublishSubject.create<Boolean>()

        pwCheckObservable.subscribe {
            check_pw_tv.visibility = if (it) View.GONE else View.VISIBLE
        }
        checkPwCheckObservable.subscribe {
            check_pw_confirm_tv.visibility = if (it) View.GONE else View.VISIBLE
        }
        nameCheckObservable.subscribe {
            check_name_tv.visibility = if (it) View.GONE else View.VISIBLE
        }
        idCheckObservable.subscribe {
            val res = if(it) R.string.notify_need_email_check else R.string.notify_check_email
            check_email_result_text_view.setText(res)
            checkEmailObservable.onNext(false)
        }
        check_email_btn.clicks().subscribe {
            showProgressBar()
            disableCheckEmailButton()

            viewModel.checkEmail(id_edit_text.text.toString(), {
                hideProgressBar()
                enableCheckEmailButton()
                check_email_result_text_view.setText(R.string.notify_unable_sign_up)
                checkEmailObservable.onNext(false)
            }, {
                hideProgressBar()
                enableCheckEmailButton()
                check_email_result_text_view.setText(R.string.notify_able_sign_up)
                checkEmailObservable.onNext(true)
            })
        }

        Observable.combineLatest(
            nameCheckObservable,
            idCheckObservable,
            pwCheckObservable,
            checkPwCheckObservable,
            checkEmailObservable,
            Function5 { nc: Boolean, ic: Boolean, pc: Boolean, cpc: Boolean, ec: Boolean ->
                sign_up_button.isEnabled = nc && ic && pc && cpc && ec
            }).subscribe()

        sign_up_button.setOnClickListener {
            createUser()
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

        viewModel.signUp(name, id, pw, {
            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
            finish()
        }, {
            enableSignUpButton()
            hideProgressBar()
            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
        })
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
