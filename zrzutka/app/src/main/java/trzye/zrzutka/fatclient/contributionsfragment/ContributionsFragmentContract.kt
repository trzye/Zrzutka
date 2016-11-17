package trzye.zrzutka.fatclient.contributionsfragment

import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.mvp.IContract

interface ContributionsFragmentContract : IContract{
    interface View : IContract.IView<Presenter>{
        fun getContributionDialogView() : ContributionDialogContract.View
    }
    interface Presenter : IContract.IPresenter<View>{
        fun createNewContribution()
    }
}

