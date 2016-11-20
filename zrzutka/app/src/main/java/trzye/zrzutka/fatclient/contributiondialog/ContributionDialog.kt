package trzye.zrzutka.fatclient.contributiondialog

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import trzye.zrzutka.R
import trzye.zrzutka.fatclient.contributionactivity.ContributionActivity
import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract
import trzye.zrzutka.databinding.DialogContributionBinding
import trzye.zrzutka.model.DatabaseService
import trzye.zrzutka.model.entity.contribution.Contribution
import java.util.*
import java.util.Calendar.*

//TODO: Not exactly MVP
class ContributionDialog(private val activity: Activity) : Dialog(activity), ContributionDialogContract.View {

    override var presenterId: Long = 0

    override var presenter: ContributionDialogContract.Presenter = ContributionDialogPresenter(DatabaseService(activity))

    lateinit var view: View
    lateinit var binding: DialogContributionBinding

    override fun start(presenter: ContributionDialogContract.Presenter) {
        show()
        this.presenter = presenter
        this.presenter.attachView(this)
        this.presenter.show()
    }

    override fun startAsCreateNewContributionDialog() {
        show()
        presenter.createNewContribution()
    }

    override fun startAsEditExistingContributionDialog(contribution: Contribution) {
        show()
        presenter.editBaseContributionData(contribution)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_contribution, null, false)
        view = binding.root
        setContentView(view)
        presenter.attachView(this)
    }

    override fun dismissView() = dismiss()

    override fun bindData(observable: Contribution) {
        binding.contribution = observable
    }

    override fun showEmptyTitleError() {
        Snackbar.make(view, R.string.empty_title_warning, Snackbar.LENGTH_SHORT).apply {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
        }.show()
    }

    override fun showDatePicker(selectedDate: Date, action: (pickedDate: Date) -> Unit, minDateToChoose: Date? ) {
        val calendar = getInstance().apply { time = Date(selectedDate.time) }
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

    override fun setActionOnOkClicked(action: () -> Unit) {
        view.findViewById(R.id.okButton).setOnClickListener{action()}
    }

    override fun getContributionTitle() = (findViewById(R.id.contributionTitleEditText) as TextView).text.toString()

    override fun getContributionActivityView(): ContributionActivityContract.View {
        return ContributionActivity(activity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.setThatJobIsDone()
    }

    override fun show() {
        super.show()
        setCanceledOnTouchOutside(false)
    }

}