package com.yujin.inColor.Base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<itemType: Any>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun onBind(position:Int, item: itemType)
}