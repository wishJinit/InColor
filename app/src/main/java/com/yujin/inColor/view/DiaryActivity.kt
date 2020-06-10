package com.yujin.inColor.view

import com.yujin.inColor.R
import com.yujin.inColor.base.BaseActivity
import com.yujin.inColor.databinding.ActivityDiaryBinding
import com.yujin.inColor.model.vo.DiaryVO
import com.yujin.inColor.viewModel.DiaryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryActivity : BaseActivity<ActivityDiaryBinding, DiaryViewModel>() {
    override val viewModel by viewModel<DiaryViewModel>()
    override val layoutResourceId: Int
        get() = R.layout.activity_diary

    override fun initSetting() {}

    override fun setDataBinding() {
        viewDataBinding.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }
    }

    override fun afterDataBinding() {
        intent.getParcelableExtra<DiaryVO>(DIARY)?.let{
            viewModel.setDiary(it)
        }
    }

    companion object {
        const val DIARY = "diary"
    }
}
