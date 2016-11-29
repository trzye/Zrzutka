package trzye.zrzutka.fatclient.editfrienddialog

import android.app.Activity
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import trzye.zrzutka.R
import trzye.zrzutka.databinding.DialogEditFriendBinding
import trzye.zrzutka.model.entity.friend.Friend

class EditFriendDialog(activity: Activity) : Dialog(activity), EditFriendDialogContract.View {

    override var presenterId: Long = 0

    override var presenter: EditFriendDialogContract.Presenter = EditFriendDialogPresenter()

    lateinit var view: View
    lateinit var binding: DialogEditFriendBinding

    override fun start(presenter: EditFriendDialogContract.Presenter) {
        show()
        this.presenter = presenter
        this.presenter.attachView(this)
        this.presenter.show()
    }

    override fun startAsEditExistingEditFriendDialog(friend: Friend, actionOnSuccess: (Friend) -> Unit, actionOnDismiss: () -> Unit) {
        show()
        presenter.editEditFriendData(friend, actionOnSuccess, actionOnDismiss)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_edit_friend, null, false)
        view = binding.root
        setContentView(view)
        presenter.attachView(this)
        binding.okButton.setOnClickListener{presenter.okClicked()}
    }

    override fun dismissView() = dismiss()

    override fun bindData(dataHolder: EditFriendDialogDataHolder) {
        binding.friendDataHolder = dataHolder
    }

    override fun showEmptyEditFriendNameError() {
        showErrorSnackBar(R.string.empty_friend_name_warning)
    }

    override fun showUsedEditFriendNameError() {
        showErrorSnackBar(R.string.used_friend_name_warning)
    }

    private fun showErrorSnackBar(stringResource: Int) {
        Snackbar.make(view, stringResource, Snackbar.LENGTH_SHORT).apply {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
        }.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.backPressed()
    }

    override fun show() {
        super.show()
        setCanceledOnTouchOutside(false)
    }

}