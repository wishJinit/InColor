package com.yujin.inphoto.view

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yujin.inphoto.Base.BaseActivity
import com.yujin.inphoto.R
import com.yujin.inphoto.ViewModel.MemberViewModel
import com.yujin.inphoto.databinding.ActivityMainBinding
import com.yujin.inphoto.view.fragment.DiaryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MemberViewModel>() {
    override lateinit var viewModel: MemberViewModel
    override val layoutResourceId: Int
        get() = R.layout.activity_main
    
    private lateinit var selectedMenuBtn: View

    override fun onDestroy() {
        viewModel.autoSignOut(this)
        super.onDestroy()
    }

    override fun initSetting() {
        viewModel = ViewModelProviders.of(this)[MemberViewModel::class.java]
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
