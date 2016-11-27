package trzye.zrzutka.fatclient.purchasedialog

import android.databinding.BaseObservable
import trzye.zrzutka.model.entity.purchase.Purchase

class PurchaseDialogDataHolder(purchase: Purchase, priceString: String) : BaseObservable(){
    var purchase = purchase
        set(value) {field = value; notifyChange()}
    var priceString = priceString
        set(value) {field = value; notifyChange()}
    val charges: List<PurchaseDialogDataHolderCharges> = purchase.charges.flatMap {
        listOf(
                PurchaseDialogDataHolderCharges(it, it.getReadableAmountToPay(), it.getReadableAmountPaid())
        )
    }

}