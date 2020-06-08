package com.yujin.inColor.model.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class DiaryVO(
    var date: Date?,
    var weather:Int?,
    var moodColor:Int?,
    var content: String?
): Parcelable