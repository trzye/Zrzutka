package trzye.zrzutka.fatclient.summaryfragment

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
import trzye.zrzutka.databinding.ItemDebtBinding
import trzye.zrzutka.fatclient.contributionfragment.AbstractContributionFragment
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentContract
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentWaitingRoom
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contribution.getDebtList

class SummaryFragment(dataHolder: ContributionDataHolder?) : AbstractContributionFragment<SummaryFragmentContract.View, SummaryFragmentContract.Presenter>(SummaryFragmentWaitingRoom, dataHolder), SummaryFragmentContract.View {

    constructor() : this(null)

    override val labelId: Int = R.string.tab_summary
    private lateinit var fragmentView: View
    private lateinit var actionButton: FloatingActionButton
    private lateinit var summaryRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_summary, null)
        fragmentView = view
        actionButton = view.findViewById(R.id.actionButton) as FloatingActionButton
//        actionButton.setOnClickListener { presenter.addNewContributor() } TODO
        summaryRecyclerView= view.findViewById(R.id.summaryRecyclerView) as RecyclerView
        summaryRecyclerView.layoutManager = LinearLayoutManager(activity)
        if(summaryRecyclerView.itemAnimator is SimpleItemAnimator){
            (summaryRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        super.onCreateView(inflater,container, savedInstanceState)
        presenter.bindData()
        return view
    }

    override fun bindData(contribution: Contribution) {
        summaryRecyclerView.adapter = SummaryAdapter(contribution)
    }

    override fun changeDataSet(contribution: Contribution) {
        (summaryRecyclerView.adapter as SummaryAdapter).apply {
            this.contribution = contribution
            notifyDataSetChanged()
        }
    }

    inner private class SummaryAdapter(var contribution: Contribution) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun getItemId(position: Int): Long {
            if(position != contribution.contributors.size){
              return contribution.contributors[position].hashCode().toLong()
            } else return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemDebtBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_debt, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            if(position != contribution.contributors.size){
                binding.debt = contribution.getDebtList()[position]
                binding.itemDebtLayout.visibility = View.VISIBLE
//              binding.contributorsRemove.setOnClickListener { presenter.removeContributor(holder.adapterPosition) } TODO
            } else {
                binding.itemDebtLayout.visibility = View.INVISIBLE
            }
        }

        override fun getItemCount(): Int = contribution.getDebtList().size + 1

        inner class ViewHolder(val binding: ItemDebtBinding) : RecyclerView.ViewHolder(binding.root)
    }

}

