package trzye.zrzutka.fatclient.contributorsfragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.R
import trzye.zrzutka.databinding.ItemContributorBinding
import trzye.zrzutka.fatclient.contributionfragment.AbstractContributionFragment
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.model.entity.contribution.Contribution

class ContributorsFragment(dataHolder: ContributionDataHolder?) : AbstractContributionFragment<ContributorsFragmentContract.View, ContributorsFragmentContract.Presenter>(ContributorsFragmentWaitingRoom, dataHolder), ContributorsFragmentContract.View {

    constructor() : this(null)

    override val labelId: Int = R.string.tab_contributors
    private lateinit var fragmentView: View
    private lateinit var actionButton: FloatingActionButton
    private lateinit var contributorsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_contributors, null)
        fragmentView = view
        actionButton = view.findViewById(R.id.actionButton) as FloatingActionButton
        actionButton.setOnClickListener { presenter.addNewContributor() }
        contributorsRecyclerView= view.findViewById(R.id.contributorsRecyclerView) as RecyclerView
        contributorsRecyclerView.layoutManager = LinearLayoutManager(activity)
        if(contributorsRecyclerView.itemAnimator is SimpleItemAnimator){
            (contributorsRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        super.onCreateView(inflater,container, savedInstanceState)
        presenter.bindData()
        return view
    }

    override fun bindData(contribution: Contribution) {
        contributorsRecyclerView.adapter = ContributorsAdapter(contribution)
    }

    override fun notifyContributorAdded(position: Int, listSize:Int) {
        contributorsRecyclerView.adapter.apply {
            notifyItemInserted(listSize)
            notifyItemRangeChanged(position, listSize)
        }
        contributorsRecyclerView.scrollToPosition(position)
    }

    override fun notifyContributorRemoved(position: Int, listSize:Int) {
        contributorsRecyclerView.adapter.apply {
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listSize)
        }
    }

    override fun hideDeleteIcons() {
        (contributorsRecyclerView.adapter as ContributorsAdapter).hideDeleteIcons = true
        contributorsRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun showDeleteIcons() {
        (contributorsRecyclerView.adapter as ContributorsAdapter).hideDeleteIcons = false
        contributorsRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun hideAddButton() {
        actionButton.visibility = View.INVISIBLE
    }

    override fun showAddButton() {
        actionButton.visibility = View.VISIBLE
    }

    override fun showContributorRemovedInfoWithUndoOption() {
        Snackbar.make(fragmentView, R.string.contributor_deleted, Snackbar.LENGTH_SHORT).apply {
            setAction(R.string.undo, { presenter.undoLastContributorRemove() })
        }.show()
    }

    override fun changeDataSet(contribution: Contribution) {
        (contributorsRecyclerView.adapter as ContributorsAdapter).apply {
            this.contribution = contribution
            notifyDataSetChanged()
        }
    }

    inner private class ContributorsAdapter(var contribution: Contribution, var hideDeleteIcons:Boolean = true) : RecyclerView.Adapter<ContributorsAdapter.ViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun getItemId(position: Int): Long {
            if(position != contribution.contributors.size){
              return contribution.contributors[position].hashCode().toLong()
            } else return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemContributorBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_contributor, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            if(position != contribution.contributors.size){
                binding.friend = contribution.contributors[position].friend
                binding.itemContributorId.visibility = View.VISIBLE
                if(hideDeleteIcons)
                    binding.contributorsRemove.visibility = View.INVISIBLE
                else {
                    binding.contributorsRemove.visibility = View.VISIBLE
                    binding.contributorsRemove.setOnClickListener { presenter.removeContributor(holder.adapterPosition) }
                }
            } else {
                binding.itemContributorId.visibility = View.INVISIBLE
            }
        }

        override fun getItemCount(): Int = contribution.contributors.size + 1

        inner class ViewHolder(val binding: ItemContributorBinding) : RecyclerView.ViewHolder(binding.root)
    }

}


