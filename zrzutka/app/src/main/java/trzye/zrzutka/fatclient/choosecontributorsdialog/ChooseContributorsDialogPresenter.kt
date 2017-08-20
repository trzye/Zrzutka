package trzye.zrzutka.fatclient.choosecontributorsdialog

import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.fatclient.readfrienddialog.ReadFriendDialogContract
import trzye.zrzutka.model.ModelProvider
import trzye.zrzutka.model.entity.friend.Friend

class ChooseContributorsDialogPresenter : ChooseContributorsDialogContract.Presenter() {

    private var isDone = false
    private var addFriendDialogPresenter: EditFriendDialogContract.Presenter? = null
    private var readFiendDialogPresenter: ReadFriendDialogContract.Presenter? = null
    lateinit var actionOnAddSelectedFriends: (List<Friend>) -> Unit
    lateinit var withoutFriends: List<Friend>
    lateinit var friends: List<ChooseContributorsDataHolder>
    val databaseService = ModelProvider.databaseService

    override fun chooseFriends(withoutFriends: List<Friend>, actionOnAddSelectedFriends: (List<Friend>) -> Unit) {
        this.withoutFriends = withoutFriends
        this.friends = getFriendsWithoutUsed()
        this.actionOnAddSelectedFriends = actionOnAddSelectedFriends
        init()
    }

    private fun getFriendsWithoutUsed(): List<ChooseContributorsDataHolder> {
        return databaseService.getAllFriends().filter {
            friend ->
            withoutFriends.find { it.databasePojo().id == friend.databasePojo().id } == null
        }.flatMap {
            listOf(ChooseContributorsDataHolder(it))
        }
    }

    private fun init() {
        view.bindData(friends)
    }

    override fun addSelectedFriends() {
        actionOnAddSelectedFriends(friends.flatMap {
            if(it.isChoosed) listOf(it.friend) else listOf()
        })
        isDone = true
        view.dismissView()
    }

    override fun startDialogsIfExists() {
        val editPresenter = addFriendDialogPresenter
        val readPresenter = readFiendDialogPresenter
        if ((editPresenter != null) && !editPresenter.isDone()) {
            view.getEditFriendDialog().start(editPresenter)
        } else if ((readPresenter != null) && !readPresenter.isDone()) {
            view.getReadFriendDialog().start(readPresenter)
        }
    }

    override fun createNewFriend() {
        addFriendDialogPresenter = view.getEditFriendDialog().apply {
            startAsEditExistingEditFriendDialog(
                    friend = Friend(""),
                    actionOnSuccess = {
                        addedFriend -> databaseService.save(addedFriend)
                        friends = getFriendsWithoutUsed()
                        view.dataSetChanged(friends)
                    },
                    actionOnDismiss = {} )
        }.presenter
    }

    override fun openFriendDialog(position: Int) {
        readFiendDialogPresenter = view.getReadFriendDialog().apply {
            startAsReadExistingReadFriendDialog(friends[position].friend, {
                friends = getFriendsWithoutUsed()
                view.dataSetChanged(friends)
            })
        }.presenter
    }

    override fun show() {
        init()
    }

    override fun isDone(): Boolean {
        return isDone
    }

    override fun backPressed() {
        isDone = true
    }
}



