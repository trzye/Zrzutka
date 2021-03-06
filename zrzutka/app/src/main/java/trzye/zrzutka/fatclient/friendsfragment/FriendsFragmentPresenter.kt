package trzye.zrzutka.fatclient.friendsfragment

import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.fatclient.readfrienddialog.ReadFriendDialogContract
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.entity.friend.Friend

class FriendsFragmentPresenter(private val databaseService: IDatabaseService) : FriendsFragmentContract.Presenter() {

    private var editFriendDialogPresenter: EditFriendDialogContract.Presenter? = null
    private var readFriendDialogPresenter: ReadFriendDialogContract.Presenter? = null
    var friends: List<Friend> = databaseService.getAllFriends()

    override fun createNewFriend() {
        editFriendDialogPresenter = view.getEditFriendDialogView().apply {
            startAsEditExistingEditFriendDialog(
                    friend = Friend(""),
                    actionOnSuccess = {
                        newFriend -> databaseService.save(newFriend)
                        friends = databaseService.getAllFriends()
                        view.dataSetChanged(friends)
                    },
                    actionOnDismiss = {} )
        }.presenter
    }

    override fun startDialogsIfExists() {
        val editPresenter = editFriendDialogPresenter
        val readPresenter = readFriendDialogPresenter
        if ((editPresenter != null) && (editPresenter.isDone() == false)) {
            view.getEditFriendDialogView().start(editPresenter)
        } else if ((readPresenter != null) && (readPresenter.isDone() == false)) {
            view.getReadFriendDialogView().start(readPresenter)
        }
    }

    override fun bindData() {
        friends = databaseService.getAllFriends()
        view.bindData(friends)
    }

    override fun openFriendDialog(position: Int){
        readFriendDialogPresenter = view.getReadFriendDialogView().apply {
            startAsReadExistingReadFriendDialog(friends[position], {
                friends = databaseService.getAllFriends()
                view.dataSetChanged(friends)
            })
        }.presenter
    }

}

