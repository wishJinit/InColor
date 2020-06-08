package com.yujin.inColor.view.fragment

import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yujin.inColor.base.BaseFragment
import com.yujin.inColor.R
import com.yujin.inColor.view.dialog.SelectDateDialog
import com.yujin.inColor.view.adapter.CalendarAdapter
import com.yujin.inColor.databinding.FragmentDiaryBinding
import com.yujin.inColor.viewModel.DiaryViewModel
import kotlinx.android.synthetic.main.fragment_diary.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiaryFragment : BaseFragment<FragmentDiaryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_diary

    override val viewModel by viewModel<DiaryViewModel>()

    private var year = 2019
    private var month = 11


    override fun initSetting() {
        setDiary()
        setEventListener()
    }

    private fun setDiary() {
        showProgressbar()

        calendar_recycler_view.run{
            adapter = CalendarAdapter()
            layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        }
        viewModel.getMonthDiary(year, month) {
            hideProgressbar()
        }
    }

    private fun setEventListener() {
        select_date_text_view.setOnClickListener {
            context?.let { c ->
                val dialog = SelectDateDialog(c, year, month) { _year, _month ->
                    year = _year
                    month = _month
                    select_date_text_view.text = "${year}년 ${month+1}월"
                    select_date_text_view.visibility = View.VISIBLE

                    viewModel.getMonthDiary(year, month) {
                        hideProgressbar()
                    }
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

}
