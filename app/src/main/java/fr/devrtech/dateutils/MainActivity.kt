package fr.devrtech.dateutils

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    // Date format
    val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // First date
    var firstDate = Calendar.getInstance()

    // Second gap date
    var secondDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    protected fun init() {

        devrtech_edit_first.setText(DATE_FORMAT.format(firstDate.time))
        devrtech_edit_second.setText(DATE_FORMAT.format(secondDate.time))

        devrtech_edit_first.setOnClickListener {
            openCalendar(firstDate, devrtech_edit_first)
        }

        devrtech_edit_second.setOnClickListener {
            openCalendar(secondDate, devrtech_edit_second)
        }

    }


    protected fun openCalendar(datetime: Calendar, editView: EditText) {

        val listener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            datetime.set(Calendar.YEAR, year)
            datetime.set(Calendar.MONTH, monthOfYear)
            datetime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            editView.setText(DATE_FORMAT.format(datetime.time))
            calculateGap()
        }

        DatePickerDialog(
            this, listener,
            datetime.get(Calendar.YEAR), datetime.get(Calendar.MONTH), datetime.get(Calendar.DAY_OF_MONTH)
        ).show()


    }

    protected fun calculateGap() {

        val gapInMillis = secondDate.timeInMillis - firstDate.timeInMillis

        val days = TimeUnit.DAYS.convert(gapInMillis, TimeUnit.MILLISECONDS)
        val weeks = days / 7
        val remainingDays = days % 7

        devrtech_text_weeks.setText(
            getString(R.string.gap_weeks, weeks, remainingDays)
        )

        devrtech_text_days.setText(
            getString(R.string.gap_days, days)
        )

    }

}
