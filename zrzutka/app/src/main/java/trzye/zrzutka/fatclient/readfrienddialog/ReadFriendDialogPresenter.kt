package trzye.zrzutka.fatclient.readfrienddialog

import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.model.ModelProvider
import trzye.zrzutka.model.entity.friend.Friend

class ReadFriendDialogPresenter() : ReadFriendDialogContract.Presenter() {

    private var isDone = false
    lateinit var friend: Friend
    private var editFriendDialogPresenter: EditFriendDialogContract.Presenter? = null
    lateinit var actionOnDelete: () -> Unit
    val databaseService = ModelProvider.databaseService

    override fun readFriendData(friend: Friend, actionOnDelete: () -> Unit) {
        this.friend = friend
        this.actionOnDelete = actionOnDelete
        init()
    }

    private fun init() {
        view.bindData(friend)
    }

    override fun editClicked() {
        editFriendDialogPresenter = view.getEditFriendDialog().apply {
            startAsEditExistingEditFriendDialog(
                    friend = friend,
                    actionOnSuccess = {
                        editedFriend -> databaseService.save(editedFriend)
                        friend = editedFriend
                        view.bindData(friend)
                    },
                    actionOnDismiss = {} )
        }.presenter
    }

    override fun startDialogsIfExists() {
        val editPresenter = editFriendDialogPresenter
        if ((editPresenter != null) && (editPresenter.isDone() == false)) {
            view.getEditFriendDialog().start(editPresenter)
        }
    }

    override fun deleteClicked() {
        if(databaseService.getAllContributions().flatMap { it._contributors }.find { it.friend.id == friend.id } == null){
            fun deleteAction() = {
                databaseService.removeFriend(friend)
                actionOnDelete()
                isDone = true
                view.dismissView()
            }
            view.showDeletePrompt(deleteAction())
        } else {
            view.showCantDeleteError()
        }
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



