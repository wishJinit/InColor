package com.yujin.inColor.viewModel

import android.widget.Toast
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
    private val _year = MutableLiveData<Int>()
    private val _month = MutableLiveData<Int>()
    private val _day = MutableLiveData<Int>()

    val year: LiveData<Int>
        get() = _year
    val month: LiveData<Int>
        get() = _month
    val day: LiveData<Int>
        get() = _day

    private val _diary = MutableLiveData<DiaryVO>()
    val diary: LiveData<DiaryVO>
        get() = _diary


    init {
        val calendar = Calendar.getInstance()
        _year.value = calendar.get(Calendar.YEAR)
        _month.value = calendar.get(Calendar.MONTH)
        _day.value = calendar.get(Calendar.DATE)
    }

    fun addDiary(diaryVO: DiaryVO, success:() -> Unit, fail:() -> Unit) {
        firebaseService.addDiary(diaryVO, success, fail)
    }

    fun getDateDiary(){
        firebaseService.getDateDiary(year.value ?: 0, month.value ?: 0, day.value ?: 0)
            ?.addOnSuccessListener {
                if(it.size() > 0) {
                    val result = it.documents[0]
                    val date = result.getDate("date")
                    val weather = result.getLong("weather")?.toInt()
                    val moodColor = result.getLong("mood_color")?.toInt()
                    val content = result.getString("content")
                    val diaryVo = DiaryVO(date, weather, moodColor, content)
                    _diary.value = diaryVo
                }
            }
    }

    fun setWeatherCode(code: Int) {
        _diary.value = _diary.value?.apply {
            weather = code
        } ?: {
            DiaryVO(Calendar.getInstance().time, code, null, null)
        }()
    }
}