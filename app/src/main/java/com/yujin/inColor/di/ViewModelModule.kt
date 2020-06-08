package com.yujin.inColor.di

import com.yujin.inColor.viewModel.DiaryViewModel
import com.yujin.inColor.viewModel.MemberViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MemberViewModel(get())
    }
    viewModel {
        DiaryViewModel(get())
    }
}