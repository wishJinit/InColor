package com.yujin.inphoto.Util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.yujin.inphoto.R
import kotlinx.android.synthetic.main.calendar_day_cell.view.*

class CalendarAdapter(calendarList: Array<Int>) : RecyclerView.Adapter<CalendarAdapter.CalendarHolder>() {
    private val _calendarList = MutableLiveData<Array<Int>>().apply {
        value = calendarList
    }
    val calendarList: LiveData<Array<Int>>
        get() = _calendarList

    override fun getItemCount(): Int {
        return calendarList.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        holder.bind(calendarList.value!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_day_cell, parent, false)
        return CalendarHolder(view)
    }
    
    inner class CalendarHolder(val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(day: Int){
            if (day != 0){
                view.day.text = day.toString()
            }
        }
    }
}