package trzye.zrzutka.fatclient.editfrienddialog

import trzye.zrzutka.model.ModelProvider
import trzye.zrzutka.model.entity.friend.Friend

class EditFriendDialogPresenter() : EditFriendDialogContract.Presenter() {

    private var isDone = false
    lateinit var actionOnSuccess: (Friend) -> Unit
    lateinit var actionOnDismiss: () -> Unit
    lateinit var dataHolder: EditFriendDialogDataHolder
    val databaseService = ModelProvider.databaseService

    override fun editEditFriendData(friend: Friend, actionOnSuccess: (Friend) -> Unit, actionOnDismiss: () -> Unit) {
        this.dataHolder = EditFriendDialogDataHolder(friend, friend.getShowingName(), friend.paymentInformation, friend.contactInformation)
        this.actionOnSuccess = actionOnSuccess
        this.actionOnDismiss = actionOnDismiss
        init()
    }

    private fun init() {
        view.bindData(dataHolder)
    }

    override fun okClicked() {
        if(dataHolder.nameString.isEmpty()){
            view.showEmptyEditFriendNameError()
            return
        }
        if(databaseService.getAllFriends().find { it.nickname == dataHolder.nameString } != null){
            view.showUsedEditFriendNameError()
            return
        }
        dataHolder.friend.nickname = dataHolder.nameString
        dataHolder.friend.contactInformation = dataHolder.contactString
        dataHolder.friend.paymentInformation = dataHolder.paymentString
        successAction()
    }

    private fun successAction() {
        actionOnSuccess(dataHolder.friend)
        view.dismissView()
        isDone = true
    }

    override fun show() {
        init()
    }

    override fun isDone(): Boolean {
        return isDone
    }

    override fun backPressed() {
        actionOnDismiss()
        isDone = true
    }
}



