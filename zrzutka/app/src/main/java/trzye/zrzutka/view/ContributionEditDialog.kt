package trzye.zrzutka.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.widget.DatePicker
import trzye.zrzutka.R
import trzye.zrzutka.controller.ContributionEditController
import trzye.zrzutka.databinding.DialogContributionEditBinding
import trzye.zrzutka.model.entity.Contribution
import java.util.*
import java.util.Calendar.*

class ContributionEditDialog(context: Context, contributionId: Long? = null) : Dialog(context), IContributionEditView{

    val _contributionId = contributionId
    lateinit var view: View
    lateinit var binding: DialogContributionEditBinding
    lateinit var controller: ContributionEditController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_contribution_edit, null, false)
        view = binding.root
        setContentView(view)
        controller = ContributionEditController(this)
    }

    override fun getContributionId(): Long? = _contributionId

    override fun showEmptyTitleError() {
        Snackbar.make(view, R.string.empty_title_warning, Snackbar.LENGTH_SHORT).apply {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
        }.show()
    }

    override fun bindData(observable: Contribution) {
        binding.contribution = observable
    }

    override fun showDatePicker(selectedDate: Date, action: (pickedDate: Date) -> Unit, minDateToChoose: Date? ) {
        val calendar = Calendar.getInstance().apply { time = Date(selectedDate.time) }
        DatePickerDialog(
                  context
                , { dp: DatePicker, y: Int, m: Int, d: Int ->
                        calendar.apply { set(YEAR, y); set(MONTH, m); set(DAY_OF_MONTH, d) }
                        action(calendar.time) }
                , calendar.get(YEAR)
                , calendar.get(MONTH)
                , calendar.get(DAY_OF_MONTH)
        ).apply {
            if(minDateToChoose != null)
                datePicker.minDate = minDateToChoose.time
        }.show()
    }

    override fun setActionOnStartDateClicked(action: () -> Unit) {
        view.findViewById(R.id.contributionStartDate).setOnClickListener{action()}
    }

    override fun setActionOnEndDateClicked(action: () -> Unit) {
        view.findViewById(R.id.contributionEndDate).setOnClickListener{action()}
    }

}