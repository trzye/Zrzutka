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

    override fun editPurchaseData(purchase: Purchase, actionOnSuccess: (Purchase) -> Unit, actionOnDismiss: () -> Unit) {
        this.dataHolder = PurchaseDialogDataHolder(purchase, getPriceText(purchase.price))
        this.actionOnSuccess = actionOnSuccess
        this.actionOnDismiss = actionOnDismiss
        init()
    }

    private fun getPriceText(price: Double) = if (price <= 0) "" else price.toReadablePriceString()

    private fun init() {
        view.bindData(dataHolder)
    }

    override fun okClicked() {
        resetProblems()
        if (!priceValidation()) return
        if(!chargesValidation()) return
        when (dataHolder.purchase.validate()) {
            PurchaseValidationStatus.OK -> successAction()
            PurchaseValidationStatus.WRONG_PAID_SUM -> view.showWrongPaidSumFormatError()
            PurchaseValidationStatus.WRONG_TO_PAY_SUM -> view.showWrongToPaySumFormatError()
        }
    }

    private fun resetProblems() {
        dataHolder.charges.forEach { it.isWrongToPay = false; it.isWrongPaid = false }
    }

    private fun chargesValidation() : Boolean {
        dataHolder.charges.forEachIndexed {
            i, it ->
            if (!toPayValidation(i, it)) return false
            if (!paidValidation(i, it)) return false
        }
        return true
    }

    private fun paidValidation(i: Int, it: PurchaseDialogDataHolderCharges): Boolean {
        try {
            it.charge.amountPaid = it.paidString.toDouble()
        } catch (e: NumberFormatException) {
            dataHolder.charges[i].isWrongPaid = true
            view.notifyChange(i)
            view.showWrongPaidFormatError()
            return false
        }
        return true
    }

    private fun toPayValidation(i: Int, it: PurchaseDialogDataHolderCharges): Boolean {
        try {
            it.charge.amountToPay = it.toPayString.toDouble()
        } catch (e: NumberFormatException) {
            dataHolder.charges[i].isWrongToPay = true
            view.notifyChange(i)
            view.showWrongToPayFormatError()
            return false
        }
        return true
    }

    private fun priceValidation(): Boolean {
        try {
            dataHolder.purchase.price = dataHolder.priceString.toDouble()
        } catch (e: NumberFormatException) {
            view.makeRedUnderPrice()
            view.showWrongPriceFormatError()
            return false
        }
        return true
    }

    override fun splitToPayCost() {
        resetProblems()
        try {
            dataHolder.purchase.price = dataHolder.priceString.toDouble()
            dataHolder.charges.forEach {
                it.charge.amountPaid = dataHolder.purchase.price / dataHolder.charges.size
                it.toPayString = it.charge.amountPaid.toReadablePriceString()
            }
        } catch (e: NumberFormatException) {
            view.showWrongPriceFormatError()
        }
    }

    private fun successAction() {
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



