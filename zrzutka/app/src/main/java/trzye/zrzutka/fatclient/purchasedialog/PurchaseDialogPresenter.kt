package trzye.zrzutka.fatclient.purchasedialog

import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.purchase.Purchase
import trzye.zrzutka.model.entity.purchase.PurchaseValidationStatus
import trzye.zrzutka.model.entity.purchase.validate

class PurchaseDialogPresenter() : PurchaseDialogContract.Presenter() {

    private var isDone = false
    lateinit var actionOnSuccess: (Purchase) -> Unit
    lateinit var actionOnDismiss: () -> Unit
    lateinit var dataHolder: PurchaseDialogDataHolder


    override fun createNewPurchase(actionOnSuccess: (Purchase) -> Unit) {
        this.dataHolder = PurchaseDialogDataHolder(Purchase("", 0.0), "")
        this.actionOnSuccess = actionOnSuccess
        this.actionOnDismiss = {}
        init()
    }

    override fun editPurchaseData(purchase: Purchase, actionOnSuccess: (Purchase) -> Unit, actionOnDismiss: () -> Unit) {
        this.dataHolder = PurchaseDialogDataHolder(purchase, purchase.price.toReadablePriceString())
        this.actionOnSuccess = actionOnSuccess
        this.actionOnDismiss = actionOnDismiss
        init()
    }

    private fun init() {
        view.bindData(dataHolder)
    }

    override fun okClicked(){
        try {
            dataHolder.purchase.price = view.getPriceStringFromInput().toDouble()
            when (dataHolder.purchase.validate()){
                PurchaseValidationStatus.OK -> successAction()
                PurchaseValidationStatus.NOT_OK -> view.showEmptyTitleError()
            }
        } catch (e : NumberFormatException) {
            view.showEmptyTitleError() //TODO
        }

    }

    private fun successAction(){
        actionOnSuccess(dataHolder.purchase)
        view.dismissView()
        isDone = true
    }

    override fun show() {
        init()
    }

    override fun isDone(): Boolean {
        return isDone
    }

    override fun backPressed() {
        actionOnDismiss()
        isDone = true
    }

}



