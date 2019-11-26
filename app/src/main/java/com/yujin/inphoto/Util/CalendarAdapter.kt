package com.yujin.inphoto.Util

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yujin.inphoto.R
import kotlinx.android.synthetic.main.calendar_day_cell.view.*

class CalendarAdapter(_calendarList: Array<Int>) : RecyclerView.Adapter<CalendarAdapter.CalendarHolder>() {
    private var calendarList = _calendarList

    private val week = 7
    private val sunday = 0
    private val saturday = 6

    private val sundayColor = "#FE4D4D"
    private val saturdayColor = "#2277AA"
    private val normalColor = "#000000"

    override fun getItemCount(): Int {
        return calendarList.size
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        val color = Color.parseColor(getColorOfWeekName(position))
        holder.bind(calendarList[position], color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_day_cell, parent, false)
        return CalendarHolder(view)
    }

    fun setCalendarList(_calendarList: Array<Int>){
        calendarList = _calendarList
        notifyDataSetChanged()
    }

    private fun getColorOfWeekName(position: Int) = when (position % week) {
        sunday -> sundayColor
        saturday -> saturdayColor
        else -> normalColor
    }

    inner class CalendarHolder(val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(day: Int, color: Int){
            if (day != 0){
                view.day.text = day.toString()
                view.day.setTextColor(color)
            } else {
                view.day.text = ""
            }
        }
    }
}