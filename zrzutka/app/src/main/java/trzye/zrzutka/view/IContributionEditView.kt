package trzye.zrzutka.view

import trzye.zrzutka.model.entity.Contribution
import java.util.*

interface IContributionEditView : IView{
    fun showEmptyTitleError()
    fun showDatePicker(selectedDate: Date, action: (pickedDate: Date) -> Unit, minDateToChoose: Date? )
    fun setActionOnStartDateClicked(action: () -> Unit)
    fun setActionOnEndDateClicked(action: () -> Unit)
    fun setActionOnOkClicked(action: () -> Unit)
    fun getContributionTitle(): String
    fun bindData(observable: Contribution)
}