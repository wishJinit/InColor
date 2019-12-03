package com.yujin.inphoto.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.yujin.inphoto.R
import com.yujin.inphoto.view.dialog.SelectDateDialog
import com.yujin.inphoto.util.CalendarAdapter
import kotlinx.android.synthetic.main.fragment_diary.*
import java.util.*


class DiaryFragment : Fragment() {
    private val _calendarList = MutableLiveData<Array<Int>>()
    val calendarList: LiveData<Array<Int>>
        get() = _calendarList

    private var year = 2019
    private var month = 11


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = GregorianCalendar()
        _calendarList.value = getCalendarList(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
        val calendarAdapter = CalendarAdapter(calendarList.value ?: Array(0){ 0 })

        _calendarList.observe(this, androidx.lifecycle.Observer { array ->
            calendarAdapter.setCalendarList(array)
        })

        calendar_recycler_view.run{
            adapter = calendarAdapter
            layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        }

        setEventListener()

    }

    private fun setEventListener() {
        select_date_text_view.setOnClickListener {
            context?.let { context ->
                val dialog = SelectDateDialog(context, year, month) { _year, _month ->
                    year = _year
                    month = _month
                    select_date_text_view.text = "${year}년 ${month}월"
                    _calendarList.value = getCalendarList(year, month - 1)
                }
                dialog.show()
            }
        }
    }

    private fun getCalendarList(_year: Int, _month: Int): Array<Int>{
        year = _year
        month = _month

        val cal = GregorianCalendar(year, month, 1)

        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1
        val lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        val emptyDayArray = Array(dayOfWeek){ 0 }
        val monthDayArray = Array(lastDayOfMonth) {i -> i + 1}

        return emptyDayArray + monthDayArray
    }

}
