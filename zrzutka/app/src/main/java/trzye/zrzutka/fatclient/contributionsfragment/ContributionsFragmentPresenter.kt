package trzye.zrzutka.fatclient.contributionsfragment

import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract

class ContributionsFragmentPresenter : ContributionsFragmentContract.Presenter() {

    private var newContributionDialogPresenter: ContributionDialogContract.Presenter? = null

    override fun createNewContribution() {
        newContributionDialogPresenter = view.getContributionDialogView().apply {
            startAsCreateNewContributionDialog()
        }.presenter
    }

    override fun startDialogIfExists() {
        val presenter = newContributionDialogPresenter
        if ((presenter != null) && (presenter.isDone() == false)) {
            view.getContributionDialogView().start(presenter)
        }
    }

}