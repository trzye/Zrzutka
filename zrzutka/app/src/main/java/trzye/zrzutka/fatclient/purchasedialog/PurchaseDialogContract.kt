package trzye.zrzutka.fatclient.purchasedialog

import trzye.zrzutka.model.entity.purchase.Purchase
import trzye.zrzutka.mvp.IContract

interface PurchaseDialogContract : IContract {

    interface View : IContract.IView<Presenter> {
        fun start(presenter: Presenter)
//        fun startAsCreateNewPurchaseDialog(actionOnOKClicked: (Purchase) -> Unit)
        fun startAsEditExistingPurchaseDialog(purchase: Purchase, actionOnSuccess: (Purchase) -> Unit, actionOnDismiss: () -> Unit)
        fun showEmptyTitleError()
        fun getPurchaseTitle(): String
        fun bindData(dataHolder: PurchaseDialogDataHolder)
//        fun getPriceStringFromInput() : String
    }

    abstract class Presenter : IContract.IPresenter<View>(){
//        abstract fun createNewPurchase(actionOnSuccess: (Purchase) -> Unit)
        abstract fun editPurchaseData(purchase: Purchase, actionOnSuccess: (Purchase) -> Unit, actionOnDismiss: () -> Unit)
        abstract fun show()
        abstract fun backPressed()
        abstract fun isDone(): Boolean
        abstract fun okClicked()
//        abstract fun saveInputState()
        abstract fun splitToPayCost()
    }
}
