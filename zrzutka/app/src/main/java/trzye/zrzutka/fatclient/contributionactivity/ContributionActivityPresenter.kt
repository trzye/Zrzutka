package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.model.IDatabaseService

class ContributionActivityPresenter(val databaseService: IDatabaseService) : ContributionActivityContract.Presenter(){

    private lateinit var dataHolder: ContributionDataHolder
    private var isContributionReceived = false
    private var editContributionDialogPresenter: ContributionDialogContract.Presenter? = null


    override fun editContribution(contributionId: Long) {
        init(contributionId)
        dataHolder.isEditable = true
    }

    override fun readContribution(contributionId: Long) {
        init(contributionId)
        dataHolder.isEditable = false
    }

    private fun init(contributionId: Long) {
        if (isContributionReceived == false) {
            dataHolder = ContributionDataHolder(databaseService.getContribution(contributionId))
            isContributionReceived = true
        }
    }

    override fun bindData() {
        view.bindData(dataHolder)
        if(dataHolder.isEditable) {
            view.setReadIcon()
            view.setToolbarClickable()
        } else {
            view.setEditIcon()
            view.setToolbarUnclickable()
        }
    }

    override fun showContributions() {
        view.getMainActivityView().startAsContributionsMainActivity(true)
        view.dismissView()
    }

    override fun setEditMode() {
        dataHolder.isEditable = true
        view.setReadIcon()
        view.setToolbarClickable()
        view.getContributionFragmentPresenters().forEach {
            it.setEditMode()
        }
    }

    override fun setReadMode() {
        dataHolder.isEditable = false
        view.setEditIcon()
        view.setToolbarUnclickable()
        view.getContributionFragmentPresenters().forEach {
            it.setReadMode()
        }
    }

    override fun editBaseContributionData() {
        editContributionDialogPresenter = view.getContributionEditDialogView().apply {
            startAsEditExistingContributionDialog(dataHolder.contribution)
        }.presenter
    }

    override fun startDialogIfExists() {
        val presenter = editContributionDialogPresenter
        if ((presenter != null) && (presenter.isDone() == false)) {
            view.getContributionEditDialogView().start(presenter)
        }
    }

}
