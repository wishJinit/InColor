package com.yujin.inphoto.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yujin.inphoto.Model.VO.DiaryVO

import com.yujin.inphoto.R
import com.yujin.inphoto.view.dialog.SelectDateDialog
import com.yujin.inphoto.util.CalendarAdapter
import com.yujin.inphoto.ViewModel.MemberViewModel
import kotlinx.android.synthetic.main.fragment_diary.*
import java.util.*
import kotlin.collections.HashMap


class DiaryFragment(private val viewModel: MemberViewModel) : Fragment() {

    private lateinit var calendarAdapter: CalendarAdapter
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
        setDatabinding()
        setDiary()
        setEventListener()
    }
    
    private fun setDatabinding(){
        viewModel.diaryDocuments.observe(this, androidx.lifecycle.Observer { documents ->
            var diaryList = HashMap<Int, DiaryVO>()

            for (diary in documents) {
                val date = diary.getDate("date")!!
                val weather = diary.getLong("weather")!!.toInt()
                val moodColor = diary.getLong("mood_color")!!.toInt()
                val content = diary.getString("content")!!

                val calendar = Calendar.getInstance()
                calendar.time = date

                diaryList[calendar.get(Calendar.DATE)] = DiaryVO(date, weather, moodColor, content)
            }

            val calendarList = getCalendarList(year, month)
            calendarAdapter.setCalendar(calendarList, diaryList)
            hideProgressbar()
        })
    }

    private fun setDiary() {
        showProgressbar()
        context?.let { context ->
            calendarAdapter = CalendarAdapter(context, arrayOf(), mapOf())

            calendar_recycler_view.run{
                adapter = calendarAdapter
                layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        viewModel.getMonthDiary(year, month)
    }

    private fun setEventListener() {
        select_date_text_view.setOnClickListener {
            context?.let { context ->
                val dialog = SelectDateDialog(context, year, month) { _year, _month ->
                    year = _year
                    month = _month
                    viewModel.getMonthDiary(year, month)
                }
                dialog.show()
            }
        }
//        menu_button.setOnClickListener {
//            PopupMenu(context, it).apply {
//                menuInflater.inflate(R.menu.calendar_menu, menu)
//                setOnMenuItemClickListener { item ->
//                    when(item.itemId) {
//                        R.id.menu_calendar -> Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
//                        R.id.menu_list -> Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
//                    }
//                    true
//                }
//            }.show()
//
//        }
    }

    private fun showProgressbar(){
        progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressbar(){
        progressbar.visibility = View.INVISIBLE
    }

    private fun getCalendarList(_year: Int, _month: Int): Array<Int>{
        year = _year
        month = _month
        select_date_text_view.text = "${year}년 ${month+1}월"
        select_date_text_view.visibility = View.VISIBLE

        val cal = GregorianCalendar(year, month, 1)

        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1
        val lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        val emptyDayArray = Array(dayOfWeek){ 0 }
        val monthDayArray = Array(lastDayOfMonth) {i -> i + 1}

        return emptyDayArray + monthDayArray
    }

}
