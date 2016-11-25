package trzye.zrzutka.fatclient.contributiondialog

import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contribution.changeEndDate
import trzye.zrzutka.model.entity.contribution.changeStartDate
import trzye.zrzutka.model.entity.contribution.changeTitle
import trzye.zrzutka.model.entity.contribution.copyBaseData

class ContributionDialogPresenter(val databaseService: IDatabaseService) : ContributionDialogContract.Presenter() {

    private var isDone = false
    lateinit var contribution: Contribution
    lateinit var onSuccess: () -> Unit


    override fun createNewContribution() {
        contribution = Contribution()
        onSuccess = {createNewContribution(contribution)}
        init(contribution)
    }

    override fun editBaseContributionData(contribution: Contribution) {
        this.contribution = contribution.clone()
        onSuccess = {editContribution(contribution, this.contribution) }
        init(this.contribution)
    }

    private fun init(contribution: Contribution) {
        view.bindData(contribution)
        view.setActionOnStartDateClicked {
            view.showDatePicker(contribution.startDate, { d -> contribution.changeStartDate(d) }, null)
        }
        view.setActionOnEndDateClicked {
            view.showDatePicker(contribution.endDate, { d -> contribution.changeEndDate(d) }, contribution.startDate)
        }
        view.setActionOnOkClicked{ contribution.changeTitle(
                title = view.getContributionTitle(),
                onSuccess = onSuccess,
                onFailure = { view.showEmptyTitleError()}
        ) }
    }

    private fun createNewContribution(contribution: Contribution){
        view.dismissView()
        val contributionId = databaseService.save(contribution)
        view.getContributionActivityView().startAsEditableContributionActivity(contributionId)
        isDone = true
    }

    private fun editContribution(target: Contribution, source: Contribution) {
        target.copyBaseData(source)
        view.dismissView()
        isDone = true
    }

    override fun show() {
        init(contribution)
    }

    override fun isDone(): Boolean {
        return isDone
    }

    override fun setThatJobIsDone() {
        isDone = true
    }

}

