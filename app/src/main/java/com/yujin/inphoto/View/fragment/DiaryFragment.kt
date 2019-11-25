package com.yujin.inphoto.view.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.yujin.inphoto.R
import com.yujin.inphoto.SelectDateDialog
import com.yujin.inphoto.Util.CalendarAdapter
import kotlinx.android.synthetic.main.fragment_diary.*
import java.util.*
import android.app.Dialog
import android.view.Window
import com.google.android.material.bottomsheet.BottomSheetDialog


class DiaryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = GregorianCalendar()
        val calendarList = getCalendarList(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
        val calendarAdapter = CalendarAdapter(calendarList)

        calendar_recycler_view.run{
            adapter = calendarAdapter
            layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        }

        setEventListener()

    }

    private fun setEventListener() {
        select_date_text_view.setOnClickListener {
            context?.let { context ->
                val dialog = SelectDateDialog(context) { year, month ->
                    val calendarList = getCalendarList(year, month)
                }
                dialog.show()
            }
        }
    }

    private fun getCalendarList(year: Int, month: Int): Array<Int>{
        val cal = GregorianCalendar(year, month, 1)

        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1
        val lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        val emptyDayArray = Array(dayOfWeek){ 0 }
        val monthDayArray = Array(lastDayOfMonth) {i -> i + 1}

        return emptyDayArray + monthDayArray
    }

}
