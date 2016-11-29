package trzye.zrzutka.fatclient.readfrienddialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import trzye.zrzutka.R
import trzye.zrzutka.databinding.DialogReadFriendBinding
import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialog
import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.model.entity.friend.Friend

class ReadFriendDialog(private val activity: Activity) : Dialog(activity), ReadFriendDialogContract.View {

    override var presenterId: Long = 0

    override var presenter: ReadFriendDialogContract.Presenter = ReadFriendDialogPresenter()

    lateinit var toolbar: Toolbar
    lateinit var view: View
    lateinit var binding: DialogReadFriendBinding

    override fun start(presenter: ReadFriendDialogContract.Presenter) {
        show()
        this.presenter = presenter
        this.presenter.attachView(this)
        this.presenter.show()
    }

    override fun startAsReadExistingReadFriendDialog(friend: Friend, actionOnDelete: () -> Unit) {
        show()
        presenter.readFriendData(friend, actionOnDelete)
    }

    override fun getEditFriendDialog(): EditFriendDialogContract.View {
        return EditFriendDialog(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_read_friend, null, false)
        view = binding.root
        setContentView(view)
        presenter.attachView(this)
        binding.actionButton.setOnClickListener{presenter.editClicked()}

        binding.readFriendToolbar.setOnMenuItemClickListener { onMenuOptionsItemSelected(it) }
        binding.readFriendToolbar.inflateMenu(R.menu.dialog_read_friend_menu)

        setOnShowListener {
            presenter.startDialogsIfExists()
        }
    }

    fun onMenuOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete -> presenter.deleteClicked()
        }
        return true
    }

    override fun dismissView() = dismiss()

    override fun bindData(friend: Friend) {
        binding.friend = friend
    }

    override fun showCantDeleteError() {
        showErrorSnackBar(R.string.cant_delete_friend)
    }

    override fun showDeletePrompt(deleteAction: () -> Unit) {
        AlertDialog.Builder(activity)
                .setTitle("Usunięcie")
                .setMessage("Czy na pewno chcesz usunąć znajomego?")
                .setPositiveButton("USUŃ", {d,i -> deleteAction()})
                .setNegativeButton("NIE USUWAJ", {d,i -> })
                .create().show()
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