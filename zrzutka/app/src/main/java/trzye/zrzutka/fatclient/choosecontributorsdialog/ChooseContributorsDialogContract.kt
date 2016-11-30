package trzye.zrzutka.fatclient.choosecontributorsdialog

import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.fatclient.readfrienddialog.ReadFriendDialogContract
import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.mvp.IContract

interface ChooseContributorsDialogContract : IContract {

    interface View : IContract.IView<Presenter> {
        fun start(presenter: Presenter)
        fun startAsChooseContributorsDialog(withoutFriends: List<Friend>, actionOnAddSelectedFriends: (List<Friend>) -> Unit)
        fun bindData(friends: List<ChooseContributorsDataHolder>)
        fun dataSetChanged(friends: List<ChooseContributorsDataHolder>)
        fun getEditFriendDialog() : EditFriendDialogContract.View
        fun getReadFriendDialog() : ReadFriendDialogContract.View
    }

    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun chooseFriends(withoutFriends: List<Friend>, actionOnAddSelectedFriends: (List<Friend>) -> Unit)
        abstract fun show()
        abstract fun backPressed()
        abstract fun isDone(): Boolean
        abstract fun addSelectedFriends()
        abstract fun startDialogsIfExists()
        abstract fun createNewFriend()
        abstract fun openFriendDialog(position: Int)
    }
}
