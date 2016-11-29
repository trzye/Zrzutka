package trzye.zrzutka.fatclient.editfrienddialog

import android.databinding.BaseObservable
import trzye.zrzutka.fatclient.purchasedialog.PurchaseDialogDataHolderCharges
import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.model.entity.purchase.Purchase

class EditFriendDialogDataHolder(friend: Friend, nameString: String, paymentString: String, contactString: String) : BaseObservable(){
    var friend = friend
        set(value) {field = value; notifyChange()}
    var nameString = nameString
        set(value) {field = value; notifyChange()}
    var paymentString = paymentString
        set(value) {field = value; notifyChange()}
    var contactString = contactString
        set(value) {field = value; notifyChange()}
}