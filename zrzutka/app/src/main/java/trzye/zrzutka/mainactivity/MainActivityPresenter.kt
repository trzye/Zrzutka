package trzye.zrzutka.mainactivity

import trzye.zrzutka.mainactivity.MainActivityContract.Presenter
import trzye.zrzutka.mainactivity.MainActivityContract.View


class MainActivityPresenter : Presenter {

    override fun getCreateNewContributionCount(): Long {
        return newContributionClickedCount
    }

    var newContributionClickedCount = 0L

    override fun attachView(view: View) {
        this.view = view
    }

    override fun createNewContribution() {
        newContributionClickedCount++
        val dialog = view.getContributionDialogView()
        dialog.showCreateNewContributionView()
    }

    lateinit var view: View

}

