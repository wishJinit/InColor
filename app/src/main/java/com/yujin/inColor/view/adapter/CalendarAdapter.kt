package com.yujin.inColor.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.yujin.inColor.base.BaseAdapter
import com.yujin.inColor.base.BaseViewHolder
import com.yujin.inColor.model.vo.CalendarVO
import com.yujin.inColor.R
import com.yujin.inColor.view.viewHolder.CalendarViewHolder
import kotlin.math.ceil

class CalendarAdapter : BaseAdapter<CalendarVO>() {
    override val layoutId: Int
        get() = R.layout.calendar_day_cell

    override fun viewHolder(layout: Int, view: View): BaseViewHolder<CalendarVO> = CalendarViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CalendarVO> {
        val width = parent.width / 7
        val height = parent.height / 5
        return super.onCreateViewHolder(parent, viewType).apply {
            itemView.layoutParams = LinearLayout.LayoutParams(width, height)
        }
    }
}