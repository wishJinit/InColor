package com.yujin.inColor.view.adapter

import android.view.View
import com.yujin.inColor.Base.BaseAdapter
import com.yujin.inColor.Base.BaseViewHolder
import com.yujin.inColor.Model.VO.CalendarVO
import com.yujin.inColor.R
import com.yujin.inColor.view.viewHolder.CalendarViewHolder

class CalendarAdapter : BaseAdapter<CalendarVO>() {
    override val layoutId: Int
        get() = R.layout.calendar_day_cell

    override fun viewHolder(layout: Int, view: View): BaseViewHolder<CalendarVO> = CalendarViewHolder(view)
}