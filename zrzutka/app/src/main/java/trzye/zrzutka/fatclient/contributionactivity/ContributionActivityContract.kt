package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.fatclient.contributionfragment.IContributionContract.IContributionPresenter
import trzye.zrzutka.fatclient.menuactivity.IMenuContract
import trzye.zrzutka.fatclient.menuactivity.IMenuContract.IMenuPresenter
import trzye.zrzutka.fatclient.menuactivity.IMenuContract.IMenuView

interface ContributionActivityContract : IMenuContract {

    interface View : IMenuView<Presenter>{
        fun startAsEditableContributionActivity(contributionId : Long)
        fun startAsReadOnlyContributionActivity(contributionId : Long)
        fun bindData(dataHolder: ContributionDataHolder)
        fun getContributionFragmentPresenters() : List<IContributionPresenter<*>>
        fun setReadIcon()
        fun setEditIcon()
        fun setToolbarClickable()
        fun setToolbarUnclickable()
        fun getContributionEditDialogView() : ContributionDialogContract.View
    }

    abstract class Presenter : IMenuPresenter<View>(){
        abstract fun bindData()
        abstract fun setEditMode()
        abstract fun setReadMode()
        abstract fun editContribution(contributionId: Long)
        abstract fun readContribution(contributionId: Long)
        abstract fun editBaseContributionData()
        abstract fun startDialogIfExists()
    }

}