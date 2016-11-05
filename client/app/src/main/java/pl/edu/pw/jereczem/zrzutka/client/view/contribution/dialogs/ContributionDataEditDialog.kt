package pl.edu.pw.jereczem.zrzutka.client.view.contribution.dialogs

import android.app.AlertDialog
import android.app.AlertDialog.BUTTON_POSITIVE
import android.app.AlertDialog.Builder
import android.app.Dialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.squareup.timessquare.CalendarPickerView
import pl.edu.pw.jereczem.zrzutka.client.R
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contribution
import pl.edu.pw.jereczem.zrzutka.client.view.common.AlertDialogs
import pl.edu.pw.jereczem.zrzutka.client.view.common.extensions.isBackPressed
import java.util.*

fun createContributionEditDialog(ctx: Context, contribution: Contribution, okAction: () -> Unit): Dialog = Builder(ctx)
        .setPositiveButton(R.string.dialog_ok, { dialogInterface, i -> })
        .create()
        .apply {
            val view = createDialogView(this)
            val title = view.findViewById(R.id.contributionTitleEditText) as TextView
            val calendar = createCalendar(contribution, view)
            setView(view)
            title.setContributionTitle(contribution, view)
            setOnBackPressedListener()
            setOnPositiveButtonActions(emptyTitleAction(view), {
                contribution.startDate = calendar.selectedDates.first()
                contribution.endDate = calendar.selectedDates.last()
                contribution.title = title.text.toString()
                okAction()
                dismiss()
            })
//            setSoftKeyboardInputMode()
        }

private fun TextView.setContributionTitle(contribution: Contribution, view: View) {
    text = contribution.title
}

private fun createDialogView(dialog: AlertDialog) =
        dialog.layoutInflater.inflate(R.layout.contribution_edit, null)


private fun createCalendar(contribution: Contribution, dialogView: View) : CalendarPickerView {
    val DATES_RANGE = 5
    val calendarView = dialogView.findViewById(R.id.contributionDateRangePicker) as CalendarPickerView
    val cMin = Date(contribution.startDate.time).apply { addYears(-DATES_RANGE) }
    val cMax = Date(contribution.startDate.time).apply { addYears(DATES_RANGE) }
    calendarView.init(cMin, cMax)
            .inMode(CalendarPickerView.SelectionMode.RANGE)
            .withSelectedDates(listOf(contribution.startDate, contribution.endDate))
    return calendarView
}

private fun Date.addYears(years : Int){
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.YEAR, years)
    time = calendar.time.time
}


private fun AlertDialog.setOnBackPressedListener() = setOnKeyListener {
    dialogInterface, i, keyEvent ->
    if (keyEvent.isBackPressed())
        AlertDialogs.backPressedAlert(context, { dialogInterface.dismiss() }, {}).show()
    true
}

private fun AlertDialog.setOnPositiveButtonActions(onEmptyTitle: () -> Unit, actionSuccess: () -> Unit) = setOnShowListener {
    getButton(BUTTON_POSITIVE).setOnClickListener {
        val editText = findViewById(R.id.contributionTitleEditText) as TextView
        if (editText.text.trim().isEmpty())
            onEmptyTitle()
        else
            actionSuccess()
    }
}

private fun emptyTitleAction(dialogView: View) = {
    Snackbar.make(dialogView, R.string.empty_title_warning, Snackbar.LENGTH_SHORT).apply {
        view.setBackgroundColor(ContextCompat.getColor(dialogView.context, R.color.colorError))
    }.show()
}

private fun AlertDialog.setSoftKeyboardInputMode() {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

