package trzye.zrzutka.fatclient.purchasedialog

import trzye.zrzutka.model.entity.purchase.Purchase
import trzye.zrzutka.mvp.IContract

interface PurchaseDialogContract : IContract {

    interface View : IContract.IView<Presenter> {
        fun start(presenter: Presenter)
        fun startAsEditExistingPurchaseDialog(purchase: Purchase, actionOnSuccess: (Purchase) -> Unit, actionOnDismiss: () -> Unit)
        fun showEmptyPurchaseNameError()
        fun showWrongPriceFormatError()
        fun showWrongToPayFormatError()
        fun showWrongPaidFormatError()
        fun showWrongToPaySumFormatError()
        fun showWrongPaidSumFormatError()
        fun getPurchaseTitle(): String
        fun bindData(dataHolder: PurchaseDialogDataHolder)
        fun makeRedUnderPrice()
        fun notifyChange(position: Int)
        fun resetSubtitleError()
    }

    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun editPurchaseData(purchase: Purchase, actionOnSuccess: (Purchase) -> Unit, actionOnDismiss: () -> Unit)
        abstract fun show()
        abstract fun backPressed()
        abstract fun isDone(): Boolean
        abstract fun okClicked()
        abstract fun splitToPayCost()
    }
}
