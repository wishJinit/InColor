package com.yujin.inphoto.Model.VO

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class DiaryVO(
    val date: Date,
    val weather:Int,
    val moodColor:Int,
    val content: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(weather)
        parcel.writeInt(moodColor)
        parcel.writeString(content)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<DiaryVO> {
        override fun createFromParcel(parcel: Parcel): DiaryVO {
            return DiaryVO(parcel)
        }

        override fun newArray(size: Int): Array<DiaryVO?> {
            return arrayOfNulls(size)
        }
    }
}