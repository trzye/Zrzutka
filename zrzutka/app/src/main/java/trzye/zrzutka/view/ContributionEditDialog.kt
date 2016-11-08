package trzye.zrzutka.view

import android.app.Dialog
import android.content.Context
//import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import com.squareup.timessquare.CalendarPickerView
import trzye.zrzutka.R
import trzye.zrzutka.common.extensions.addYears
//import trzye.zrzutka.databinding.DialogContributionEditBinding
import trzye.zrzutka.model.entity.Contribution
import java.util.*

class ContributionEditDialog(context: Context, contributionId: Long? = null) : Dialog(context), IContributionEditView{

    val _contributionId = contributionId
    lateinit var view: View
//    lateinit var binding: DialogContributionEditBinding
    lateinit var calendarView: CalendarPickerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.dialog_contribution_edit, null)
        calendarView = findViewById(R.id.contributionDateRangePicker) as CalendarPickerView
//        binding = DataBindingUtil.setContentView(ownerActivity, R.layout.dialog_contribution_edit)
        setContentView(view)
    }

    override fun getContributionId(): Long? = _contributionId

    override fun showEmptyTitleError() {
        Snackbar.make(view, R.string.empty_title_warning, Snackbar.LENGTH_SHORT).apply {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
        }.show()

    }

    override fun bindData(observable: Contribution) {
//        binding.contribution = observable
//        createCalendar(observable)
    }

    private fun createCalendar(contribution: Contribution) {
        val datesYearRange = 5
        val cMin = Date(contribution.startDate.time).apply { addYears(-datesYearRange) }
        val cMax = Date(contribution.startDate.time).apply { addYears(datesYearRange) }
        calendarView.init(cMin, cMax)
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(listOf(contribution.startDate, contribution.endDate))
    }


}