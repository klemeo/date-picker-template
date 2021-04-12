package ru.android.simplecalendar

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import java.time.LocalDate
import java.time.ZoneId

class AppDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        private const val ARG_THEME_RES_ID = "arg.theme.res.id"
        private const val ARG_DATE = "arg.date"
        private const val ARG_DATE_MAX = "arg.date.max"
        private const val ARG_DATE_MIN = "arg.min.max"
        private const val ARG_BTN_NEGATIVE_TEXT = "arg.btn.negative.button"
        private const val ARG_BTN_POSITIVE_TEXT = "arg.btn.positive.button"

        private const val DEFAULT_THEME_RES_ID = R.style.DatePickerThemeReg

        fun create(
            @StyleRes themeResId: Int = DEFAULT_THEME_RES_ID,
            selectedDate: LocalDate? = LocalDate.now(),
            maxDate: LocalDate? = LocalDate.now().plusMonths(1),
            minDate: LocalDate? = LocalDate.now().minusYears(100),
            @StringRes buttonNegativeText: Int = R.string.common_cancel,
            @StringRes buttonPositiveText: Int = R.string.common_pick
        ): AppDatePickerDialog {
            val args = bundleOf(
                ARG_THEME_RES_ID to themeResId,
                ARG_DATE to selectedDate,
                ARG_DATE_MAX to maxDate,
                ARG_DATE_MIN to minDate,
                ARG_BTN_NEGATIVE_TEXT to buttonNegativeText,
                ARG_BTN_POSITIVE_TEXT to buttonPositiveText
            )
            return AppDatePickerDialog().apply {
                arguments = args
            }
        }
    }

    private lateinit var maxDate: LocalDate
    private lateinit var minDate: LocalDate


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = createBaseDialog()
        maxDate = arguments?.getSerializable(ARG_DATE_MAX) as? LocalDate ?: LocalDate.now()
        minDate = arguments?.getSerializable(ARG_DATE_MIN) as? LocalDate
            ?: LocalDate.now().minusYears(100)
        val btnNegativeText = arguments?.getInt(ARG_BTN_NEGATIVE_TEXT, R.string.common_cancel)
            ?: R.string.common_cancel
        val btnPositiveText =
            arguments?.getInt(ARG_BTN_POSITIVE_TEXT, R.string.common_pick) ?: R.string.common_pick
        dialog.apply {
            datePicker.maxDate =
                maxDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            datePicker.minDate =
                minDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            setButton(
                DatePickerDialog.BUTTON_NEGATIVE,
                context.getString(btnNegativeText)
            ) { _, _ ->

                }
            setButton(
                DatePickerDialog.BUTTON_POSITIVE,
                context.getString(btnPositiveText),
                ::handlePositiveClick
            )
        }
        return dialog
    }

    private fun createBaseDialog(): DatePickerDialog {
        val selectedDate = arguments?.getSerializable(ARG_DATE) as? LocalDate ?: LocalDate.now()
        val themeResId =
            arguments?.getInt(ARG_THEME_RES_ID, DEFAULT_THEME_RES_ID) ?: DEFAULT_THEME_RES_ID
        return DatePickerDialog(
            requireContext(), themeResId, this,
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth
        )
    }

    private fun handlePositiveClick(dialog: DialogInterface, which: Int) {
        val datePicker = (dialog as? DatePickerDialog)?.datePicker ?: return
        val pickedDate = LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
        if (pickedDate.isAfter(maxDate)) {

        } else {
            dialog.onClick(dialog, which)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }
}
