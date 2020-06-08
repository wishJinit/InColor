package com.yujin.inColor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yujin.inColor.base.BaseViewModel
import com.yujin.inColor.model.FirebaseService
import com.yujin.inColor.model.vo.CalendarVO
import com.yujin.inColor.model.vo.DiaryVO
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DiaryViewModel(private val firebaseService: FirebaseService) : BaseViewModel() {
    private val _calendarList = MutableLiveData<ArrayList<CalendarVO>>()
    val calendarList: LiveData<ArrayList<CalendarVO>>
        get() = _calendarList

    fun addDiary(diaryVO: DiaryVO) {
        firebaseService.addDiary(diaryVO)
    }

    fun getMonthDiary(year:Int, month:Int, success:() -> Unit){
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1, 0, 0, 0)
        val startDate = calendar.time
        calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0, 0)
        val finishDate = calendar.time

        firebaseService.getMonthDiary(startDate, finishDate)
            ?.addOnSuccessListener { result ->
                val dateList = getCalendarList(year, month)
                val diaryMap = HashMap<Int, DiaryVO>()
                val calendarAl = ArrayList<CalendarVO>()

                result.forEach { diary ->
                    val date = diary.getDate("date")
                    val weather = diary.getLong("weather")?.toInt()
                    val moodColor = diary.getLong("mood_color")?.toInt()
                    val content = diary.getString("content")

                    date?.let {
                        calendar.time = it
                        diaryMap[calendar.get(Calendar.DATE)] = DiaryVO(it, weather, moodColor, content)
                    }
                }

                dateList.forEach {
                    if(it != 0) {
                        calendarAl.add(CalendarVO(it, diaryMap[it]))
                    }
                }

                _calendarList.value = calendarAl
                success()
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