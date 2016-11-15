package trzye.zrzutka.mainactivity

import trzye.zrzutka.contributiondialog.ContributionDialogContract
import trzye.zrzutka.mvp.IContract


interface MainActivityContract : IContract {

    interface View : IContract.IView<Presenter> {
        fun showContributionAddedMessage()
        fun getContributionDialogView() : ContributionDialogContract.View
    }

    interface Presenter : IContract.IPresenter<View> {
        fun createNewContribution()
        fun getCreateNewContributionCount() : Long
    }

}
