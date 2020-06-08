package com.yujin.inColor.view.fragment

import android.graphics.drawable.GradientDrawable
import android.view.View
import com.yujin.inColor.base.BaseFragment
import com.yujin.inColor.model.vo.DiaryVO

import com.yujin.inColor.R
import com.yujin.inColor.util.ColorUtil
import com.yujin.inColor.databinding.FragmentWriteBinding
import com.yujin.inColor.viewModel.DiaryViewModel
import kotlinx.android.synthetic.main.fragment_write.*
import java.util.*


class WriteFragment(private val viewModel: DiaryViewModel) : BaseFragment<FragmentWriteBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_write

    private val year: Int
    private val month: Int
    private val day: Int

    private var weather: View? = null
    private var weatherNum = 0
    private var mood: View? = null
    private var moodNum = 0

    init {
        val cal = GregorianCalendar()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH) + 1
        day = cal.get(Calendar.DATE)
    }

    override fun initSetting() {
        date_text_view.text = "${year}년 ${month}월 ${day}일 일기"

        setEventListener()
        setMoodColor()
    }

    private fun setEventListener() {
        add_diary_btn.setOnClickListener {
            val date = Date()
            val content = content_edit_text.text.toString()

            val diary = DiaryVO(date, weatherNum, moodNum, content)
            viewModel.addDiary(diary)
        }
    }

    private fun setMoodColor(){
        context?.let { context ->
            for (i in 1..5) {
                val moodViewNm = "mood_${i}_view"
                val moodColorNm = "moodColorRed${i}"

                val moodView = view?.findViewById<View>(resources.getIdentifier(moodViewNm, "id", context.packageName))
                val moodColor = ColorUtil.getColorId(context, moodColorNm)

                if (moodView?.background is GradientDrawable) {
                    val background = moodView.background as GradientDrawable
                    background.setColor(moodColor)
                    background.setStroke(5, ColorUtil.getColorId(context, R.color.colorLightGray))
                }
            }
        }
    }

    private fun setMoodStrokeColor(v: View){
        context?.let { context ->
            val strokeColor = if(v.isSelected){
                R.color.colorGray
            } else {
                R.color.colorLightGray
            }

            if (v.background is GradientDrawable) {
                val background = v.background as GradientDrawable
                background.setStroke(5, ColorUtil.getColorId(context, strokeColor))
            }
        }
    }

    fun selectWeather(v: View, _weatherNum: Int){
        weather?.let {
            it.isSelected = false
        }
        v.isSelected = true
        weather = v
        weatherNum = _weatherNum
    }

    fun selectMoodColor(v: View, _moodNum: Int){
        mood?.let {
            it.isSelected = false
            setMoodStrokeColor(it)
        }
        v.isSelected = true
        setMoodStrokeColor(v)
        mood = v
        moodNum = _moodNum
    }

}
