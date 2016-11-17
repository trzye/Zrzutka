package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.fatclient.mainactivity.MainActivityWaitingRoom
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.entity.Contribution

class ContributionActivityPresenter(val databaseService: IDatabaseService) : ContributionActivityContract.Presenter{

    private lateinit var view: ContributionActivityContract.View
    private lateinit var contribution: Contribution
    private var isContributionReceived = false

    override fun editContribution(contributionId: Long) {
        if(isContributionReceived == false) {
            contribution = databaseService.getContribution(contributionId)
            isContributionReceived = true
        }
    }


    override fun attachView(view: ContributionActivityContract.View) {
        this.view = view
    }

    override fun bindData() {
        view.bindData(contribution)
    }

    override fun showContributions() {
        view.getMainActivityView().startAsContributionsMainActivity()
        view.dismissView()
    }

}
