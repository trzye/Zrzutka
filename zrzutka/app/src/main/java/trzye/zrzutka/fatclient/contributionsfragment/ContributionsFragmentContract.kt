package trzye.zrzutka.fatclient.contributionsfragment

import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.model.dto.ContributionsDTO
import trzye.zrzutka.mvp.IContract

interface ContributionsFragmentContract : IContract{
    interface View : IContract.IView<Presenter>{
        fun getContributionDialogView() : ContributionDialogContract.View
        fun bindData(contributionsDTO: ContributionsDTO)
        fun dataSetChanged(contributionsDTO: ContributionsDTO)
        fun notifyContributionChanged(position: Int)
        fun notifyContributionAdded(position: Int, listSize: Int)
        fun setToolbarForEdition()
        fun setToolbarForStandardMode()
        fun showOneContributionRemovedInfo()
        fun showContributionsRemovedInfo()
        fun hideInformationAboutRemovingContribution()
        fun getContributionActivityView() : ContributionActivityContract.View
    }
    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun bindData()
        abstract fun createNewContribution()
        abstract fun openContributionInReadMode(position: Int)
        abstract fun openContributionInEditMode(position: Int)
        abstract fun changeCheckedOption(position: Int)
        abstract fun removeCheckedContributions()
        abstract fun undoRemovingContributions()
        abstract fun mergeCheckedContributions()
        abstract fun startDialogIfExists()
        abstract fun setToolbar()
    }
}

