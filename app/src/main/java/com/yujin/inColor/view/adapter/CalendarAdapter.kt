package com.yujin.inColor.view.adapter

import android.view.View
import com.yujin.inColor.base.BaseAdapter
import com.yujin.inColor.base.BaseViewHolder
import com.yujin.inColor.model.vo.CalendarVO
import com.yujin.inColor.R
import com.yujin.inColor.view.viewHolder.CalendarViewHolder

class CalendarAdapter : BaseAdapter<CalendarVO>() {
    override val layoutId: Int
        get() = R.layout.calendar_day_cell

    override fun viewHolder(layout: Int, view: View): BaseViewHolder<CalendarVO> = CalendarViewHolder(view)
}