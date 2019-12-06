package com.yujin.inphoto.Model.VO

import java.util.*

data class DiaryVO(
    val date: Date,
    val weather:Int,
    val moodColor:Int,
    val content: String
)