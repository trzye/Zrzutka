package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.model.IDatabaseService

class ContributionActivityPresenter(val databaseService: IDatabaseService) : ContributionActivityContract.Presenter(){

    private lateinit var dataHolder: ContributionDataHolder
    private var isContributionReceived = false

    override fun editContribution(contributionId: Long, isEditable: Boolean) {
        if(isContributionReceived == false) {
            dataHolder = ContributionDataHolder(databaseService.getContribution(contributionId), isEditable)
            isContributionReceived = true
        }
    }

    override fun bindData() {
        view.bindData(dataHolder)
    }

    override fun showContributions() {
        view.getMainActivityView().startAsContributionsMainActivity(true)
        view.dismissView()
    }

    override fun setEditMode() {
        throw UnsupportedOperationException("not implemented")
    }

    override fun setReadMode() {
        throw UnsupportedOperationException("not implemented")
    }

}
