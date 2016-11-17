package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.model.entity.Contribution
import trzye.zrzutka.mvp.IMenuContract

interface ContributionActivityContract : IMenuContract {

    interface View : IMenuContract.IMenuView<Presenter>{
        fun getContributorsFragmentView() : ContributorsFragmentContract.View
        fun getPurchasesFragmentView() : PurchasesFragmentContract.View
        fun getSummaryFragmentView() : SummaryFragmentContract.View
        fun startAsEditableContributionActivity(contributionId : Long)
        fun bindData(contribution: Contribution)
    }

    interface Presenter : IMenuContract.IMenuPresenter<View>{
        fun editContribution(contributionId: Long)
        fun bindData()
    }


}