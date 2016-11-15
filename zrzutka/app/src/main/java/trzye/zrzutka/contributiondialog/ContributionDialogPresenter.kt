package trzye.zrzutka.contributiondialog

import android.app.Activity
import trzye.zrzutka.model.DatabaseService
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.entity.Contribution
import trzye.zrzutka.model.extensions.changeEndDate
import trzye.zrzutka.model.extensions.changeStartDate
import trzye.zrzutka.model.extensions.changeTitle
import trzye.zrzutka.model.extensions.copyBaseData

class ContributionDialogPresenter(activity: Activity) : ContributionDialogContract.Presenter {

    lateinit var view: ContributionDialogContract.View
    val databaseService: IDatabaseService = DatabaseService(activity)

    override fun attachView(view: ContributionDialogContract.View) {
        this.view = view
    }

    override fun createNewContribution() {
        val contribution: Contribution = Contribution()
        init(contribution)
        view.setActionOnOkClicked{
            contribution.changeTitle(
                    title = view.getContributionTitle(),
                    onSuccess = { createNewContribution(contribution) },
                    onFailure = { view.showEmptyTitleError() }
            )
        }
    }

    override fun editBaseContributionData(contribution: Contribution) {
        val editableContribution = contribution.clone()
        init(editableContribution)
        view.setActionOnOkClicked{ editableContribution.changeTitle(
                title = view.getContributionTitle(),
                onSuccess = { editContribution(contribution, editableContribution) },
                onFailure = { view.showEmptyTitleError()}
        ) }
    }

    private fun init(contribution: Contribution) {
        view.bindData(contribution)
        view.setActionOnStartDateClicked {
            view.showDatePicker(contribution.startDate, { d -> contribution.changeStartDate(d) }, null)
        }
        view.setActionOnEndDateClicked {
            view.showDatePicker(contribution.endDate, { d -> contribution.changeEndDate(d) }, contribution.startDate)
        }
    }

    private fun createNewContribution(contribution: Contribution){
        view.dismissView()
        val contributionId = databaseService.save(contribution)
        // TODO
    }

    private fun editContribution(target: Contribution, source: Contribution) {
        target.copyBaseData(source)
        view.dismissView()
    }

}

