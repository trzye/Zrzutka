package trzye.zrzutka.fatclient.choosecontributorsdialog

import android.app.Activity
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.R
import trzye.zrzutka.databinding.ItemChooseContributorBinding
import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialog
import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.fatclient.readfrienddialog.ReadFriendDialog
import trzye.zrzutka.fatclient.readfrienddialog.ReadFriendDialogContract
import trzye.zrzutka.model.entity.friend.Friend

class ChooseContributorsDialog(private val activity: Activity) : Dialog(activity), ChooseContributorsDialogContract.View {

    override var presenterId: Long = 0

    override var presenter: ChooseContributorsDialogContract.Presenter = ChooseContributorsDialogPresenter()

    lateinit var view: View
    lateinit var friendsRecyclerView : RecyclerView

    override fun start(presenter: ChooseContributorsDialogContract.Presenter) {
        show()
        this.presenter = presenter
        this.presenter.attachView(this)
        this.presenter.show()
    }

    override fun startAsChooseContributorsDialog(withoutFriends: List<Friend>, actionOnAddSelectedFriends: (List<Friend>) -> Unit){
        show()
        presenter.chooseFriends(withoutFriends, actionOnAddSelectedFriends)
    }

    override fun getEditFriendDialog(): EditFriendDialogContract.View {
        return EditFriendDialog(activity)
    }

    override fun getReadFriendDialog(): ReadFriendDialogContract.View {
        return ReadFriendDialog(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.dialog_choose_contributors, null)
        setContentView(view)
        presenter.attachView(this)

        findViewById(R.id.actionAddSelected).setOnClickListener { presenter.addSelectedFriends() }

        (findViewById(R.id.chooseToolbar) as Toolbar).setOnMenuItemClickListener { onMenuOptionsItemSelected(it) }
        (findViewById(R.id.chooseToolbar) as Toolbar).inflateMenu(R.menu.dialog_choose_contributors_menu)

        friendsRecyclerView = findViewById(R.id.contributorsToChooseRecyclerView) as RecyclerView
        friendsRecyclerView.layoutManager = LinearLayoutManager(activity)


        setOnShowListener {
            presenter.startDialogsIfExists()
        }
    }

    fun onMenuOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add_friend -> presenter.createNewFriend()
        }
        return true
    }

    override fun dismissView() = dismiss()

    override fun bindData(friends: List<ChooseContributorsDataHolder>) {
        friendsRecyclerView.adapter = FriendsAdapter(friends)
    }

    override fun dataSetChanged(friends: List<ChooseContributorsDataHolder>) {
        (friendsRecyclerView.adapter as FriendsAdapter).apply {
            this.friends = friends
            notifyDataSetChanged()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.backPressed()
    }

    override fun show() {
        super.show()
        setCanceledOnTouchOutside(false)
    }

    inner private class FriendsAdapter(var friends: List<ChooseContributorsDataHolder>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun getItemId(position: Int): Long {
            if(position != friends.size){
                return friends[position].hashCode().toLong()
            } else return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemChooseContributorBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_friend, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            if(position != friends.size){
                binding.itemContributorId.visibility = View.VISIBLE
                binding.chooseContributor = friends[position]
                binding.itemContributorId.setOnClickListener { presenter.openFriendDialog(holder.adapterPosition) }
            } else {
                binding.itemContributorId.visibility = View.INVISIBLE
            }
        }

        override fun getItemCount(): Int = friends.size + 1

        inner class ViewHolder(val binding: ItemChooseContributorBinding) : RecyclerView.ViewHolder(binding.root)
    }

}