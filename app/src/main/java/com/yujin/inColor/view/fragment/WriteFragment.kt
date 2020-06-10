package com.yujin.inColor.view.fragment

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.Toast
import com.yujin.inColor.base.BaseFragment
import com.yujin.inColor.model.vo.DiaryVO

import com.yujin.inColor.R
import com.yujin.inColor.util.ColorUtil
import com.yujin.inColor.databinding.FragmentWriteBinding
import com.yujin.inColor.viewModel.DiaryViewModel
import kotlinx.android.synthetic.main.fragment_write.*
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class WriteFragment : BaseFragment<FragmentWriteBinding>() {
    override val viewModel by viewModel<DiaryViewModel>()
    override val layoutId: Int
        get() = R.layout.fragment_write

    private var mood: View? = null
    private var moodNum = 0

    override fun initSetting() {
        setDataBinding()
        setEventListener()
        setMoodColor()
    }

    private fun setDataBinding() {
        binding.let {
            it.lifecycleOwner = this
            it.activity = this
            it.viewModel = viewModel.apply {
                getDateDiary()
            }
        }
    }

    private fun setEventListener() {
        add_diary_btn.setOnClickListener {
            val date = Date()
            val content = content_edit_text.text.toString()

            val diary = DiaryVO(date, viewModel.diary.value?.weather, moodNum, content)
            viewModel.addDiary(diary, {
                Toast.makeText(context, "다이어리 저장 성공", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(context, "다이어리 저장 실패", Toast.LENGTH_SHORT).show()
            })
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
