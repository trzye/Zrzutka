package trzye.zrzutka.fatclient.readfrienddialog

import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.mvp.IContract

interface ReadFriendDialogContract : IContract {

    interface View : IContract.IView<Presenter> {
        fun start(presenter: Presenter)
        fun startAsReadExistingReadFriendDialog(friend: Friend, actionOnDelete: () -> Unit)
        fun bindData(friend: Friend)
        fun getEditFriendDialog() : EditFriendDialogContract.View
        fun showCantDeleteError()
        fun showDeletePrompt(deleteAction: () -> Unit)
    }

    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun readFriendData(friend: Friend, actionOnDelete: () -> Unit)
        abstract fun show()
        abstract fun backPressed()
        abstract fun isDone(): Boolean
        abstract fun editClicked()
        abstract fun deleteClicked()
        abstract fun startDialogsIfExists()
    }
}
