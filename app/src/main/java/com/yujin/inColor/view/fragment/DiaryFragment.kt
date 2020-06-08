package com.yujin.inColor.view.fragment

import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yujin.inColor.Base.BaseFragment
import com.yujin.inColor.Model.VO.DiaryVO

import com.yujin.inColor.R
import com.yujin.inColor.view.dialog.SelectDateDialog
import com.yujin.inColor.view.adapter.CalendarAdapter
import com.yujin.inColor.databinding.FragmentDiaryBinding
import com.yujin.inColor.viewModel.DiaryViewModel
import kotlinx.android.synthetic.main.fragment_diary.*
import java.util.*
import kotlin.collections.HashMap
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiaryFragment : BaseFragment<FragmentDiaryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_diary

    private val viewModel by viewModel<DiaryViewModel>()

    private lateinit var calendarAdapter: CalendarAdapter
    private var year = 2019
    private var month = 11


    override fun initSetting() {
        setDataBinding()
        setDiary()
        setEventListener()
    }
    
    private fun setDataBinding(){
        viewModel.diaryDocuments.observe(this, androidx.lifecycle.Observer { documents ->
            val diaryList = HashMap<Int, DiaryVO>()

            for (diary in documents) {
                val date = diary.getDate("date")
                val weather = diary.getLong("weather")?.toInt()
                val moodColor = diary.getLong("mood_color")?.toInt()
                val content = diary.getString("content")

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
            calendarAdapter = CalendarAdapter(
                context,
                arrayOf(),
                mapOf()
            )

            calendar_recycler_view.run{
                adapter = calendarAdapter
                layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        viewModel.getMonthDiary(year, month)
    }

    private fun setEventListener() {
        select_date_text_view.setOnClickListener {
            context?.let { c ->
                val dialog = SelectDateDialog(c, year, month) { _year, _month ->
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
