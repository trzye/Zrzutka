package trzye.zrzutka.fatclient.contributionsfragment

import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract

class ContributionsFragmentPresenter : ContributionsFragmentContract.Presenter {

    private lateinit var view: ContributionsFragmentContract.View
    private var newContributionDialogPresenter: ContributionDialogContract.Presenter? = null

    override fun createNewContribution() {
        newContributionDialogPresenter = view.getContributionDialogView().apply {
            startAsCreateNewContributionDialog()
        }.presenter
    }

    override fun attachView(view: ContributionsFragmentContract.View) {
        this.view = view
        startDialogIfExists(view)
    }

    private fun startDialogIfExists(view: ContributionsFragmentContract.View) {
        val presenter = newContributionDialogPresenter
        if ((presenter != null) && (presenter.isDone() == false)) {
            view.getContributionDialogView().start(presenter)
        }
    }

}