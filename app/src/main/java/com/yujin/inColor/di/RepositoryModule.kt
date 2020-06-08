package com.yujin.inColor.di

import com.yujin.inColor.Model.FirebaseService
import org.koin.dsl.module

val repositoryModule = module {
    single { FirebaseService() }
}