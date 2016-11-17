package trzye.zrzutka.fatclient.contributionsfragment

class ContributionsFragmentPresenter : ContributionsFragmentContract.Presenter {

    override fun createNewContribution() {
        view.getContributionDialogView().startAsCreateNewContributionDialog()
    }

    lateinit var view: ContributionsFragmentContract.View

    override fun attachView(view: ContributionsFragmentContract.View) {
        this.view = view
    }

}