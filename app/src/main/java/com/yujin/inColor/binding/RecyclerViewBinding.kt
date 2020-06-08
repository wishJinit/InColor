package com.yujin.inColor.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yujin.inColor.model.vo.CalendarVO
import com.yujin.inColor.view.adapter.CalendarAdapter

@BindingAdapter("adapterDiaryList")
fun setDiaryList(view: RecyclerView, calendarList: ArrayList<CalendarVO>?) {
    calendarList?.let {
        (view.adapter as CalendarAdapter).run {
            setList(it)
        }
    }
}