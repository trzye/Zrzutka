package trzye.zrzutka.fatclient.friendsfragment

import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.mvp.IContract

interface FriendsFragmentContract : IContract {
    interface View : IContract.IView<Presenter> {
        fun getReadFriendDialogView() : ReadFriendDialogContract.View
        fun getEditFriendDialogView() : EditFriendDialogContract.View
        fun bindData(friends: List<Friend>)
        fun dataSetChanged(friends: List<Friend>)
        fun notifyFriendChanged(position: Int)
        fun notifyFriendAdded(position: Int, listSize: Int)
    }
    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun bindData()
        abstract fun createNewFriend()
        abstract fun openFriendDialog(position: Int)
        abstract fun startDialogsIfExists()
    }
}

interface ReadFriendDialogContract : IContract{
    interface View : IContract.IView<Presenter>{
        fun start(presenter: Presenter)
    }
    abstract class Presenter : IContract.IPresenter<View>(){
        abstract fun isDone(): Boolean
    }
}
