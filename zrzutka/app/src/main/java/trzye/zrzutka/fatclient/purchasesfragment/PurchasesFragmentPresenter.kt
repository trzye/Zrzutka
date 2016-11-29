package trzye.zrzutka.fatclient.purchasesfragment

import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.purchasesfragment.PurchasesFragmentContract
import trzye.zrzutka.fatclient.purchasedialog.PurchaseDialogContract
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contribution.addPurchase
import trzye.zrzutka.model.entity.contribution.removePurchase
import trzye.zrzutka.model.entity.purchase.Purchase

class PurchasesFragmentPresenter(private val databaseService: IDatabaseService) : PurchasesFragmentContract.Presenter() {

    private var newPurchaseDialogPresenter: PurchaseDialogContract.Presenter? = null
    private lateinit var lastState: Contribution
    private lateinit var dataHolder: ContributionDataHolder
    private val openedPurchases: MutableList<Purchase> = mutableListOf()

    override fun init(dataHolder: ContributionDataHolder) {
        this.dataHolder = dataHolder
    }

    override fun bindData() {
        view.bindData(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
        dataHolder.contribution.purchases.forEachIndexed { i, purchase ->
            if(openedPurchases.contains(purchase)){
                view.showPurchaseData(i)
            }
        }
    }

    override fun addNewPurchase() {
        if(dataHolder.contribution.contributors.isNotEmpty()) {
            val toEdit = dataHolder.contribution.doCopy()
            val purchaseToEdit = Purchase("", 0)
            toEdit.addPurchase(purchaseToEdit)

            val actionOnOKClicked = { purchase: Purchase ->
                dataHolder.contribution = toEdit
                databaseService.save(dataHolder.contribution)
                view.changeDataSet(dataHolder.contribution)
            }

            val actionOnDismiss = {}

            newPurchaseDialogPresenter = view.getPurchaseDialogView().apply {
                startAsEditExistingPurchaseDialog(purchaseToEdit, actionOnOKClicked, actionOnDismiss)
            }.presenter
        } else {
            view.showNoContributorsErrorMessage()
        }
    }

    override fun startDialogIfExists() {
        val presenter = newPurchaseDialogPresenter
        if ((presenter != null) && (presenter.isDone() == false)) {
            view.getPurchaseDialogView().start(presenter)
        }
    }

    override fun removePurchase(position: Int) {
        if(dataHolder.isEditable){
            lastState = dataHolder.contribution.doCopy()
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

    override fun editPurchaseData(position: Int) {

        val toEdit = dataHolder.contribution.doCopy()

        val actionOnOKClicked = { purchase: Purchase ->
            dataHolder.contribution = toEdit
            databaseService.save(dataHolder.contribution)
            view.changeDataSet(dataHolder.contribution)
        }

        val actionOnDismiss = {}

        newPurchaseDialogPresenter = view.getPurchaseDialogView().apply {
            startAsEditExistingPurchaseDialog(toEdit.purchases[position], actionOnOKClicked, actionOnDismiss)
        }.presenter
    }


    override fun collapsePurchaseData(position: Int) {
        val purchase = dataHolder.contribution.purchases[position]
        if(openedPurchases.contains(purchase)){
            view.hidePurchaseData(position)
            openedPurchases.remove(purchase)
        } else {
            view.showPurchaseData(position)
            openedPurchases.add(purchase)
        }
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



