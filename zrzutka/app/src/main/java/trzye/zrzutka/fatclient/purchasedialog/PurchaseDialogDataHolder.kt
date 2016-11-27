package trzye.zrzutka.fatclient.purchasedialog

import android.databinding.BaseObservable
import trzye.zrzutka.model.entity.purchase.Purchase

class PurchaseDialogDataHolder(purchase: Purchase, priceString: String) : BaseObservable(){
    var purchase = purchase
        set(value) {field = value; notifyChange()}
    var priceString = priceString
        set(value) {field = value; notifyChange()}
}