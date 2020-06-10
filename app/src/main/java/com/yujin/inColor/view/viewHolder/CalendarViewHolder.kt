package com.yujin.inColor.view.viewHolder

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.yujin.inColor.base.BaseViewHolder
import com.yujin.inColor.model.vo.CalendarVO
import com.yujin.inColor.util.ColorUtil
import com.yujin.inColor.view.DiaryActivity
import kotlinx.android.synthetic.main.calendar_day_cell.view.*

class CalendarViewHolder(val view : View) : BaseViewHolder<CalendarVO>(view) {
    override fun onBind(position: Int, item: CalendarVO) {
        if(item.day != 0) {
            view.day.text = item.day.toString()
            view.day.setTextColor(Color.parseColor(getColorOfWeekName(position)))
            item.diary?.let { diary ->
                val moodColorNm = "moodColorRed${diary.moodColor}"
                view.mood_color_layout.background =
                    ColorUtil.getColorDrawable(
                        view.context,
                        moodColorNm
                    )
                view.setOnClickListener {
                    val intent = Intent(view.context, DiaryActivity::class.java)
                    intent.putExtra(DiaryActivity.DIARY, diary)
                    view.context.startActivity(intent)
                }
            }
        } else {
            view.day.text = ""
            view.mood_color_layout.background = ColorDrawable(Color.TRANSPARENT)
        }
    }

    private fun getColorOfWeekName(position: Int) = when (position % WEEK) {
        SUNDAY -> SUNDAY_RGB
        SATURDAY -> SATURDAY_RGB
        else -> NORMAL_RGB
    }

    companion object {
        private const val WEEK = 7
        private const val SUNDAY = 0
        private const val SATURDAY = 6

        private const val SUNDAY_RGB = "#FE4D4D"
        private const val SATURDAY_RGB = "#2277AA"
        private const val NORMAL_RGB = "#000000"
    }
}