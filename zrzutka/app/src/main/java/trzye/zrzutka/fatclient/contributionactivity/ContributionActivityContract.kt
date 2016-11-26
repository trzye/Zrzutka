package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.fatclient.menuactivity.IMenuContract

interface ContributionActivityContract : IMenuContract {

    interface View : IMenuContract.IMenuView<Presenter>{
        fun startAsEditableContributionActivity(contributionId : Long)
        fun startAsReadOnlyContributionActivity(contributionId : Long)
        fun bindData(dataHolder: ContributionDataHolder)
        fun getContributionFragmentPresenters() : List<IContributionContract.IContributionPresenter<*>>
        fun setReadIcon()
        fun setEditIcon()
        fun setToolbarClickable()
        fun setToolbarUnclickable()
        fun getContributionEditDialogView() : ContributionDialogContract.View
    }

    abstract class Presenter : IMenuContract.IMenuPresenter<View>(){
        abstract fun bindData()
        abstract fun setEditMode()
        abstract fun setReadMode()
        abstract fun editContribution(contributionId: Long)
        abstract fun readContribution(contributionId: Long)
        abstract fun editBaseContributionData()
        abstract fun startDialogIfExists()
    }


}