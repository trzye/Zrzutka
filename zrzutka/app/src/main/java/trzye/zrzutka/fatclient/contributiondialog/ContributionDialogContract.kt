package trzye.zrzutka.fatclient.contributiondialog

import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.mvp.IContract
import java.util.*

interface ContributionDialogContract : IContract{

    interface View : IContract.IView<Presenter>{
        fun start(presenter: Presenter)
        fun startAsCreateNewContributionDialog()
        fun startAsEditExistingContributionDialog(contribution: Contribution)
        fun showEmptyTitleError()
        fun showDatePicker(selectedDate: Date, action: (pickedDate: Date) -> Unit, minDateToChoose: Date? )
        fun setActionOnStartDateClicked(action: () -> Unit)
        fun setActionOnEndDateClicked(action: () -> Unit)
        fun setActionOnOkClicked(action: () -> Unit)
        fun getContributionTitle(): String
        fun bindData(observable: Contribution)
        fun getContributionActivityView() : ContributionActivityContract.View
    }

    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun createNewContribution()
        abstract fun editBaseContributionData(contribution: Contribution)
        abstract fun show()
        abstract fun setThatJobIsDone()
        abstract fun isDone(): Boolean
    }
}
