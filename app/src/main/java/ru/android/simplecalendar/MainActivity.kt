package ru.android.simplecalendar

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCalendar.setOnClickListener {
            showDatePickerDialogCalendar()
        }

        buttonSpinner.setOnClickListener {
            showDatePickerDialogSpinner()
        }

    }

    private fun showDatePickerDialogSpinner() {
        AppDatePickerDialog
            .create()
            .show(supportFragmentManager, "datePicker")
    }

    private fun showDatePickerDialogCalendar() {
        AppDatePickerDialog
            .create(
                themeResId = R.style.DatePickerTheme_Any
            )
            .show(supportFragmentManager, "datePicker")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }

}