package com.yujin.inphoto.util

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

object ColorUtil {

    fun getColorDrawable(context: Context, resNm:String):Drawable{
        val color = getColorId(context, resNm)
        return ColorDrawable(color)
    }

    fun getColorId(context:Context, resNm:String): Int{
        val colorId = context.resources.getIdentifier(resNm, "color", context.packageName)
        return getColorId(context, colorId)
    }

    fun getColorId(context: Context, resId:Int):Int{
        return ContextCompat.getColor(context, resId)
    }
}