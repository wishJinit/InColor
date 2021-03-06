package com.yujin.inColor.view

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.yujin.inColor.base.BaseActivity
import com.yujin.inColor.R
import com.yujin.inColor.viewModel.MemberViewModel
import com.yujin.inColor.databinding.ActivityMainBinding
import com.yujin.inColor.view.fragment.DiaryFragment
import com.yujin.inColor.view.fragment.WriteFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding, MemberViewModel>() {
    override val viewModel by viewModel<MemberViewModel>()
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    
    private lateinit var selectedMenuBtn: View

    override fun onDestroy() {
        viewModel.autoSignOut(this)
        super.onDestroy()
    }

    override fun initSetting() {
        changeContentView(DiaryFragment())

        selectedMenuBtn = diary_btn
        selectedMenuBtn.isSelected = true
    }

    override fun setDataBinding() {
        viewDataBinding.activity = this
    }

    override fun afterDataBinding() {
    }

    fun clickMenuBtn(v:View){
        selectedMenuBtn.isSelected = false

        selectedMenuBtn = v
        selectedMenuBtn.isSelected = true

        when(v.id){
            diary_btn.id -> changeContentView(DiaryFragment())
            write_btn.id -> changeContentView(WriteFragment())
            sign_out_btn.id -> signOut()
        }
    }

    private fun signOut(){
        viewModel.clearAutoSignIn(this)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
     }

    private fun changeContentView(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contents_view_main, fragment)
        transaction.commit()
    }
}
