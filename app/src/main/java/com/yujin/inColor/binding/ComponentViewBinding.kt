package com.yujin.inColor.binding

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.yujin.inColor.R
import com.yujin.inColor.util.ColorUtil
import com.yujin.inColor.util.DiaryUtil

@BindingAdapter("isSelected")
fun setSelected(v: View, isSelected: Boolean) {
    v.isSelected = isSelected
}

@BindingAdapter("weatherImg")
fun setWeatherImg(v: View, weatherCode: Int) {
    if(weatherCode > 0) {
        with(DiaryUtil) {
            val weatherImg = when(weatherCode) {
                weatherSun -> R.drawable.sun
                weatherCloud -> R.drawable.cloud
                weatherRain -> R.drawable.rain
                weatherWind -> R.drawable.wind
                weatherSnow -> R.drawable.snow
                weatherLight -> R.drawable.lighting
                else -> R.drawable.nothing
            }
            v.background = ContextCompat.getDrawable(v.context, weatherImg)
        }
    }
}

@BindingAdapter("redMoodColor")
fun setRedMoodColor(v: View, moodCode: Int) {

    val context = v.context

    val moodColorNm = "moodColorRed${moodCode}"
    val moodColor = if (moodCode > 0) {
        ColorUtil.getColorId(context, moodColorNm)
    } else {
        R.color.colorDarkGray
    }

    v.setBackgroundColor(moodColor)
}