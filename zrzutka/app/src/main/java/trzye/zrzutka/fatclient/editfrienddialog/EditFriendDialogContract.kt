package trzye.zrzutka.fatclient.editfrienddialog

import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.mvp.IContract

interface EditFriendDialogContract : IContract {

    interface View : IContract.IView<Presenter> {
        fun start(presenter: Presenter)
        fun startAsEditExistingEditFriendDialog(friend: Friend, actionOnSuccess: (Friend) -> Unit, actionOnDismiss: () -> Unit)
        fun showEmptyEditFriendNameError()
        fun showUsedEditFriendNameError()
        fun bindData(dataHolder: EditFriendDialogDataHolder)
    }

    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun editEditFriendData(friend: Friend, actionOnSuccess: (Friend) -> Unit, actionOnDismiss: () -> Unit)
        abstract fun show()
        abstract fun backPressed()
        abstract fun isDone(): Boolean
        abstract fun okClicked()
    }
}
