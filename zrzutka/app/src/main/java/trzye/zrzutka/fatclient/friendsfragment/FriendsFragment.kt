package trzye.zrzutka.fatclient.friendsfragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.R
import trzye.zrzutka.androidmvp.AbstractFragment
import trzye.zrzutka.databinding.ItemFriendBinding
import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialog
import trzye.zrzutka.fatclient.editfrienddialog.EditFriendDialogContract
import trzye.zrzutka.fatclient.friendsfragment.FriendsFragmentContract.Presenter
import trzye.zrzutka.fatclient.readfrienddialog.ReadFriendDialog
import trzye.zrzutka.fatclient.readfrienddialog.ReadFriendDialogContract
import trzye.zrzutka.model.entity.friend.Friend

class FriendsFragment() : AbstractFragment<FriendsFragmentContract.View, Presenter>(FriendsFragmentWaitingRoom), FriendsFragmentContract.View {

    private lateinit var fragmentView: View
    private lateinit var friendsRecyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater,container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_friends, null)

        fragmentView = view

        val actionButton = view.findViewById(R.id.actionButton) as FloatingActionButton
        actionButton.setOnClickListener { presenter.createNewFriend() }

        friendsRecyclerView = view.findViewById(R.id.friendsRecyclerView) as RecyclerView
        friendsRecyclerView.layoutManager = LinearLayoutManager(activity)

        activity.title = resources.getString(R.string.menu_friends_list)

        presenter.startDialogsIfExists()
        presenter.bindData()
        return view
    }

    override fun bindData(friends: List<Friend>) {
        friendsRecyclerView.adapter = FriendsAdapter(friends)
    }

    override fun getReadFriendDialogView(): ReadFriendDialogContract.View {
        return ReadFriendDialog(activity)
    }

    override fun getEditFriendDialogView(): EditFriendDialogContract.View {
        return EditFriendDialog(activity)
    }

    override fun dataSetChanged(friends: List<Friend>) {
        (friendsRecyclerView.adapter as FriendsAdapter).apply {
            this.friends = friends
            notifyDataSetChanged()
        }
    }

    override fun notifyFriendAdded(position: Int, listSize: Int) {
        friendsRecyclerView.adapter.apply {
            notifyItemInserted(listSize)
            notifyItemRangeChanged(position, listSize)
        }
        friendsRecyclerView.scrollToPosition(position)
    }

    override fun notifyFriendChanged(position: Int) {
        friendsRecyclerView.adapter.apply {
            notifyItemChanged(position)
        }
    }

    inner private class FriendsAdapter(var friends: List<Friend>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun getItemId(position: Int): Long {
            if(position != friends.size){
                return friends[position].hashCode().toLong()
            } else return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemFriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_friend, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            if(position != friends.size){
                binding.itemFriendId.visibility = View.VISIBLE
                binding.friend = friends[position]
                binding.itemFriendId.setOnClickListener { presenter.openFriendDialog(holder.adapterPosition) }
            } else {
                binding.itemFriendId.visibility = View.INVISIBLE
            }
        }

        override fun getItemCount(): Int = friends.size + 1

        inner class ViewHolder(val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root)
    }

}



