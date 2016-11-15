package trzye.zrzutka.contributiondialog

import trzye.zrzutka.model.entity.Contribution
import trzye.zrzutka.mvp.IContract
import java.util.*

interface ContributionDialogContract : IContract{

    interface View : IContract.IView<Presenter>{
        fun showCreateNewContributionView()
        fun showEditContributionView(contribution: Contribution)
        fun showEmptyTitleError()
        fun showDatePicker(selectedDate: Date, action: (pickedDate: Date) -> Unit, minDateToChoose: Date? )
        fun setActionOnStartDateClicked(action: () -> Unit)
        fun setActionOnEndDateClicked(action: () -> Unit)
        fun setActionOnOkClicked(action: () -> Unit)
        fun getContributionTitle(): String
        fun bindData(observable: Contribution)
    }

    interface Presenter : IContract.IPresenter<View>{
        fun createNewContribution()
        fun editBaseContributionData(contribution: Contribution)
    }
}
