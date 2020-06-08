package com.yujin.inColor.view.fragment

import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yujin.inColor.base.BaseFragment
import com.yujin.inColor.R
import com.yujin.inColor.view.dialog.SelectDateDialog
import com.yujin.inColor.view.adapter.CalendarAdapter
import com.yujin.inColor.databinding.FragmentDiaryBinding
import com.yujin.inColor.viewModel.CalendarViewModel
import kotlinx.android.synthetic.main.fragment_diary.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiaryFragment : BaseFragment<FragmentDiaryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_diary

    override val viewModel by viewModel<CalendarViewModel>()

    override fun initSetting() {
        setDataBinding()
        setDiary()
        setEventListener()
    }

    private fun setDataBinding() {
        binding.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }
    }

    private fun setDiary() {
        showProgressbar()

        calendar_recycler_view.run{
            adapter = CalendarAdapter()
            layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        }
        viewModel.getMonthDiary {
            setDateText(viewModel.year, viewModel.month)
            hideProgressbar()
        }
    }

    private fun setEventListener() {
        select_date_text_view.setOnClickListener {
            context?.let { c ->
                val dialog = SelectDateDialog(c, viewModel.year, viewModel.month) { year, month ->
                    setDateText(year, month)
                    viewModel.setDate(year, month) {
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

    private fun setDateText(year: Int, month: Int) {
        select_date_text_view.text = "${year}년 ${month+1}월"
        select_date_text_view.visibility = View.VISIBLE
    }
}
