package trzye.zrzutka.fatclient.friendsfragment

import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.entity.friend.Friend

class FriendsFragmentPresenter(private val databaseService: IDatabaseService) : FriendsFragmentContract.Presenter() {

    private var editFriendDialogPresenter: EditFriendDialogContract.Presenter? = null
    private var readFriendDialogPresenter: ReadFriendDialogContract.Presenter? = null
    var friends: List<Friend> = databaseService.getAllFriends()

    override fun createNewFriend() {
        editFriendDialogPresenter = view.getEditFriendDialogView().apply {
//            startAsCreateNewContributionDialog() TODO
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
//            startAsCreateNewContributionDialog() TODO
        }.presenter
    }

}

