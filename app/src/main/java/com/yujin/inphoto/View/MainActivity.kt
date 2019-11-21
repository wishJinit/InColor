package com.yujin.inphoto.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.yujin.inphoto.Base.BaseActivity
import com.yujin.inphoto.R
import com.yujin.inphoto.ViewModel.MemberViewModel
import com.yujin.inphoto.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MemberViewModel>() {
    override lateinit var viewModel: MemberViewModel
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        viewModel.autoSignOut(this)
        super.onDestroy()
    }

    override fun initSetting() {
        viewModel = ViewModelProviders.of(this)[MemberViewModel::class.java]
    }

    override fun setDataBinding() {
        viewDataBinding.activity = this
    }

    override fun afterDataBinding() {
    }

    fun clickMenuBtn(v:View){
        when(v.id){
            sign_out_btn.id -> signOut()
        }
    }

    private fun signOut(){
        viewModel.clearAutoSignIn(this)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
     }
}
