package trzye.zrzutka.controller

import trzye.zrzutka.model.DatabaseService
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.entity.Contribution
import trzye.zrzutka.view.IContributionEditView
import java.util.*

class ContributionEditController(val view: IContributionEditView) : IContributionEditController {

    val databaseService: IDatabaseService = DatabaseService(view.getContext())
    val contribution: Contribution = databaseService.getContribution(view.getContributionId())

    init {
        view.bindData(contribution)
        view.setActionOnStartDateClicked {
            view.showDatePicker(contribution.startDate, {d -> changeStartDate(d)}, null)
        }
        view.setActionOnEndDateClicked {
            view.showDatePicker(contribution.endDate, {d -> changeEndDate(d)}, contribution.startDate)
        }
    }

    override fun changeTitle(title: String) {
        if(title.trim().isEmpty())
            view.showEmptyTitleError()
        else
            contribution.title = title
    }

    override fun changeStartDate(date: Date) {
        contribution.startDate = date
        if(contribution.startDate > contribution.endDate)
            contribution.endDate = Date(contribution.startDate.time)
    }

    override fun changeEndDate(date: Date) {
        contribution.endDate = date
    }

}

