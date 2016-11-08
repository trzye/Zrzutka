package trzye.zrzutka.view

import trzye.zrzutka.model.entity.Contribution
import java.util.*

interface IContributionEditView : IView<Contribution> {
    fun getContributionId(): Long?
    fun showEmptyTitleError()
    fun showDatePicker(selectedDate: Date, action: (pickedDate: Date) -> Unit, minDateToChoose: Date? )
    fun setActionOnStartDateClicked(action: () -> Unit)
    fun setActionOnEndDateClicked(action: () -> Unit)
}