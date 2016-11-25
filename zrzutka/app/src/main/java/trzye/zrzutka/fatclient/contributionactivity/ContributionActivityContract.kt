package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.menuactivity.IMenuContract

interface ContributionActivityContract : IMenuContract {

    interface View : IMenuContract.IMenuView<Presenter>{
        fun startAsEditableContributionActivity(contributionId : Long)
        fun bindData(dataHolder: ContributionDataHolder)
    }

    abstract class Presenter : IMenuContract.IMenuPresenter<View>(){
        abstract fun bindData()
        abstract fun setEditMode()
        abstract fun setReadMode()
        abstract fun editContribution(contributionId: Long, isEditable: Boolean)
    }


}