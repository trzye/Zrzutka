package trzye.zrzutka.fatclient.contributionsfragment

import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.mvp.IContract

interface ContributionsFragmentContract : IContract{
    interface View : IContract.IView<Presenter>{
        fun getContributionDialogView() : ContributionDialogContract.View
    }
    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun createNewContribution()
        abstract fun startDialogIfExists()
    }
}

