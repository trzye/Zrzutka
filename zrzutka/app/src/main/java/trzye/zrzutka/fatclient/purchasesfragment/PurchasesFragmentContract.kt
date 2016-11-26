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
        fun hidePurchaseRemovedInfoWithUndoOption()
        fun showPurchaseData(position: Int)
        fun hidePurchaseData(position: Int)
        fun showPurchaseEditDialog(position: Int)
    }

    abstract class Presenter : IContributionContract.IContributionPresenter<View>() {
        abstract fun addNewPurchase()
        abstract fun removePurchase(position: Int)
        abstract fun undoLastPurchaseRemove()
        abstract fun editPurchaseData(position: Int)
        abstract fun bindData()
        abstract fun collapsePurchaseData(position: Int)
    }
}

