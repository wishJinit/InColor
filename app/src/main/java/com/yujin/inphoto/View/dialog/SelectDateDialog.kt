package com.yujin.inphoto.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.yujin.inphoto.R
import kotlinx.android.synthetic.main.dialog_select_date.*
import java.util.*

class SelectDateDialog(context: Context, private val year: Int, private val month: Int, val selectDate: (year: Int, month: Int) -> Unit) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_select_date)

        settingDialog()
    }

    private fun settingDialog(){
        setPicker()
        setEventListener()
    }

    private fun setPicker(){
        val yearRange = 10
        val selectYear = GregorianCalendar().get(Calendar.YEAR)

        year_number_picker.minValue = selectYear - yearRange
        year_number_picker.maxValue = selectYear + yearRange

        month_number_picker.minValue = 1
        month_number_picker.maxValue = 12

        year_number_picker.value = year
        month_number_picker.value = month + 1

        year_number_picker.wrapSelectorWheel = false
        month_number_picker.wrapSelectorWheel = false
    }

    private fun setEventListener(){
        close_btn.setOnClickListener {
            dismiss()
        }

        select_btn.setOnClickListener {
            val year = year_number_picker.value
            val month = month_number_picker.value - 1

            selectDate(year, month)
            dismiss()
        }
    }

}
