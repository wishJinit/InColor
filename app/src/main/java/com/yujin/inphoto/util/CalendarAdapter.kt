package com.yujin.inphoto.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yujin.inphoto.Model.VO.DiaryVO
import com.yujin.inphoto.R
import com.yujin.inphoto.view.DiaryActivity
import kotlinx.android.synthetic.main.calendar_day_cell.view.*

class CalendarAdapter(val context:Context, _calendarList: Array<Int>, _diaryList: Map<Int, DiaryVO>) : RecyclerView.Adapter<CalendarAdapter.CalendarHolder>() {
    private var calendarList = _calendarList
    private var diaryList = _diaryList

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
        val day = calendarList[position]
        holder.bind(day, color, diaryList[day])
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
        fun bind(day: Int, color: Int, diary: DiaryVO?){
            if (day != 0){
                view.day.text = day.toString()
                view.day.setTextColor(color)
                diary?.let {
                    val moodColorNm = "moodColorRed${diary.moodColor}"
                    view.mood_color_layout.background = ColorUtil.getColorDrawable(context, moodColorNm)
                    view.setOnClickListener {
                        val intent = Intent(context, DiaryActivity::class.java)

                        val bundle = Bundle()
                        bundle.putInt("moodColor", diary.moodColor)
                        bundle.putInt("weather", diary.weather)
                        bundle.putString("content", diary.content)
                        intent.putExtra("Diary", bundle)

                        context.startActivity(intent)
                    }
                }
            } else {
                view.day.text = ""
                view.mood_color_layout.background = ColorDrawable(Color.TRANSPARENT)
            }
        }
    }
}