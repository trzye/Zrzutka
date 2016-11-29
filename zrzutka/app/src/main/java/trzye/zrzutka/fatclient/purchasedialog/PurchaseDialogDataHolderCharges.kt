package trzye.zrzutka.fatclient.purchasedialog

import android.databinding.BaseObservable
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.purchase.Purchase

class PurchaseDialogDataHolderCharges(charge: Charge, toPayString: String, paidString: String) : BaseObservable(){
    var charge = charge
        set(value) {field = value; notifyChange()}
    var toPayString = toPayString
        set(value) {field = value; notifyChange()}
    var paidString = paidString
        set(value) {field = value; notifyChange()}
    var isWrongPaid = false
    var isWrongToPay = false
}