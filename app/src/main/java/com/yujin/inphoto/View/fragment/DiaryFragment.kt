package com.yujin.inphoto.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yujin.inphoto.R
import java.util.*


class DiaryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calList = getCalendarList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }

    private fun getCalendarList(): Array<Int>{
        val cal = GregorianCalendar()

        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1
        val lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        val emptyDayArray = Array(dayOfWeek){ 0 }
        val monthDayArray = Array(lastDayOfMonth) {i -> i + 1}

        return emptyDayArray + monthDayArray
    }

}
