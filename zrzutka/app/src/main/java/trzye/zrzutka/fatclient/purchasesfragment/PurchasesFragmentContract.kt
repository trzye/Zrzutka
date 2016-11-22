package trzye.zrzutka.fatclient.contributorsfragment

import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.model.entity.contribution.Contribution

interface PurchasesFragmentContract : IContributionContract {

    interface View : IContributionContract.IContributionView<Presenter>{
        fun bindData(contribution: Contribution)
        fun changeDataSet(contribution: Contribution)
        fun notifyPurchaseAdded(position: Int, listSize: Int)
        fun notifyPurchaseRemoved(position: Int, listSize: Int)
        fun hideDeleteIcons()
        fun showDeleteIcons()
        fun hideAddButton()
        fun showAddButton()
        fun showPurchaseRemovedInfoWithUndoOption()
        fun showPurchase(position: Int)
    }

    interface Presenter : IContributionContract.IContributionPresenter<View> {
        fun addNewPurchase()
        fun removePurchase(position: Int)
        fun undoLastPurchaseRemove()
        fun showPurchaseData(position: Int)
    }
}

