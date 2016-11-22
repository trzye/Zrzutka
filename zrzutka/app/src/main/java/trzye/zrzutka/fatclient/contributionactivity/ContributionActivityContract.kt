package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.fatclient.menuactivity.IMenuContract

interface ContributionActivityContract : IMenuContract {

    interface View : IMenuContract.IMenuView<Presenter>{
        fun startAsEditableContributionActivity(contributionId : Long)
        fun bindData(dataHolder: ContributionDataHolder)
    }

    interface Presenter : IMenuContract.IMenuPresenter<View>{
        fun bindData()
        fun setEditMode()
        fun setReadMode()
        fun editContribution(contributionId: Long, isEditable: Boolean)
    }


}