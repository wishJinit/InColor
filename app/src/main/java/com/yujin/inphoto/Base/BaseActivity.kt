package com.yujin.inphoto.Base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<viewDataBinding : ViewDataBinding, baseViewModel : BaseViewModel> : AppCompatActivity() {

    lateinit var viewDataBinding: viewDataBinding
    abstract var viewModel: baseViewModel
    abstract val layoutResourceId: Int

    abstract fun initSetting()
    abstract fun setDataBinding()
    abstract fun afterDataBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)

        initSetting()
        setDataBinding()
        afterDataBinding()
    }
}
