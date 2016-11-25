package trzye.zrzutka.fatclient.purchasesfragment

import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.contributorsfragment.PurchasesFragmentContract
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.purchase.Purchase

class PurchasesFragmentPresenter() : PurchasesFragmentContract.Presenter() {

    private lateinit var lastState: Contribution
    private lateinit var dataHolder: ContributionDataHolder

    override fun init(dataHolder: ContributionDataHolder) {
        this.dataHolder = dataHolder
    }

    override fun bindData() {
        this.view = view
        view.bindData(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }

    override fun addNewPurchase() {
        dataHolder.contribution.addPurchase(Purchase("TEST").apply {
            this._charges.addAll(dataHolder.contribution.contributors.flatMap { listOf(Charge(it, this)) })
        }) //TODO
        view.notifyPurchaseAdded(dataHolder.contribution.purchases.size-1, dataHolder.contribution.purchases.size)
    }

    override fun removePurchase(position: Int) {
        if(dataHolder.isEditable){
            lastState = dataHolder.contribution.clone()
            val purchase = dataHolder.contribution.purchases.getOrNull(position)
            if(purchase != null){
                dataHolder.contribution.removePurchase(purchase)
                view.notifyPurchaseRemoved(position, dataHolder.contribution.purchases.size)
                view.showPurchaseRemovedInfoWithUndoOption()
            }
        }
    }

    override fun undoLastPurchaseRemove() {
        dataHolder.contribution = lastState
        view.changeDataSet(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }

    override fun showPurchaseData(position: Int) {
        view.showPurchase(position)
    }

    override fun setEditMode() {
        dataHolder.isEditable = true
        view.showAddButton()
        view.showDeleteIcons()
    }

    override fun setReadMode() {
        dataHolder.isEditable = false
        view.hideAddButton()
        view.hideDeleteIcons()
    }

    override fun updateView(view: PurchasesFragmentContract.View) {
        this.view = view
        view.changeDataSet(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }
}

//TODO
private fun Contribution.removePurchase(purchase: Purchase) {
    this._purchases.remove(purchase)
}

//TODO
private fun Contribution.addPurchase(purchase: Purchase) {
    this._purchases.add(purchase)
}

