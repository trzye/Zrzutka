package trzye.zrzutka.fatclient.purchasesfragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.R
import trzye.zrzutka.databinding.ItemChargeBinding
import trzye.zrzutka.databinding.ItemPurchaseBinding
import trzye.zrzutka.fatclient.contributionfragment.AbstractContributionFragment
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.contributorsfragment.PurchasesFragmentContract
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.getColor
import trzye.zrzutka.model.entity.purchase.Purchase

class PurchasesFragment(dataHolder: ContributionDataHolder?) : AbstractContributionFragment<PurchasesFragmentContract.View, PurchasesFragmentContract.Presenter>(PurchasesFragmentWaitingRoom, dataHolder), PurchasesFragmentContract.View {

    constructor() : this(null)

    override val labelId: Int = R.string.tab_purchases
    private lateinit var fragmentView: View
    private lateinit var actionButton: FloatingActionButton
    private lateinit var purchasesRecyclerView: RecyclerView
    private lateinit var snackbar: Snackbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_purchases, null)
        fragmentView = view
        actionButton = view.findViewById(R.id.actionButton) as FloatingActionButton
        actionButton.setOnClickListener { presenter.addNewPurchase() }
        purchasesRecyclerView= view.findViewById(R.id.purchasesRecyclerView) as RecyclerView
        purchasesRecyclerView.layoutManager = LinearLayoutManager(activity)
        snackbar = makePurchaseRemoveInfoSnackBar()
        if(purchasesRecyclerView.itemAnimator is SimpleItemAnimator){
            (purchasesRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        super.onCreateView(inflater,container, savedInstanceState)
        presenter.bindData()
        return view
    }

    override fun bindData(contribution: Contribution) {
        purchasesRecyclerView.adapter = PurchasesAdapter(contribution)
    }

    override fun showPurchaseEditDialog(position: Int) {
        //TODO
    }

    override fun showPurchaseData(position: Int) {
        (purchasesRecyclerView.adapter as PurchasesAdapter).setVisible(position)
    }

    override fun hidePurchaseData(position: Int) {
        (purchasesRecyclerView.adapter as PurchasesAdapter).setGone(position)
    }

    override fun notifyPurchaseAdded(position: Int, listSize:Int) {
        purchasesRecyclerView.adapter.apply {
            notifyItemInserted(listSize)
            notifyItemRangeChanged(position, listSize)
        }
        purchasesRecyclerView.scrollToPosition(position)
    }

    override fun notifyPurchaseRemoved(position: Int, listSize:Int) {
        purchasesRecyclerView.adapter.apply {
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listSize)
        }
    }

    override fun hideDeleteIcons() {
        (purchasesRecyclerView.adapter as PurchasesAdapter).hideDeleteIcons = true
        purchasesRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun showDeleteIcons() {
        (purchasesRecyclerView.adapter as PurchasesAdapter).hideDeleteIcons = false
        purchasesRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun hideAddButton() {
        actionButton.visibility = View.INVISIBLE
    }

    override fun showAddButton() {
        actionButton.visibility = View.VISIBLE
    }

    override fun showPurchaseRemovedInfoWithUndoOption() {
        snackbar = makePurchaseRemoveInfoSnackBar()
        snackbar.show()
    }

    override fun hidePurchaseRemovedInfoWithUndoOption() {
        snackbar.dismiss()
    }

    private fun makePurchaseRemoveInfoSnackBar() : Snackbar{
        return Snackbar.make(fragmentView, R.string.purchase_deleted, Snackbar.LENGTH_SHORT).apply {
            setAction(R.string.undo, { presenter.undoLastPurchaseRemove() })
        }
    }

    override fun changeDataSet(contribution: Contribution) {
        (purchasesRecyclerView.adapter as PurchasesAdapter).apply {
            this.contribution = contribution
            notifyDataSetChanged()
        }
    }

    inner private class PurchasesAdapter(var contribution: Contribution, var hideDeleteIcons:Boolean = true) : RecyclerView.Adapter<PurchasesAdapter.ViewHolder>() {

        init {
            setHasStableIds(true)
        }

        private val visiblePurchases: MutableList<Purchase> = mutableListOf()

        override fun getItemId(position: Int): Long {
            if(position != contribution.purchases.size){
                return contribution.purchases[position].hashCode().toLong()
            } else return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemPurchaseBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_purchase, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            if(position != contribution.purchases.size){
                binding.purchase = contribution.purchases[position]
                binding.itemPurchaseId.visibility = View.VISIBLE
                val layoutManager = LinearLayoutManager(context)
                binding.chargesListItem.layoutManager = layoutManager
                binding.chargesListItem.adapter = ChargesAdapter(contribution.purchases[position])
                binding.chargesListItem.setHasFixedSize(true)
                if(binding.chargesListItem.itemAnimator is SimpleItemAnimator){
                    (binding.chargesListItem.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                }
                if(hideDeleteIcons){
                    binding.actionDeletePurchase.visibility = View.GONE
                    binding.actionEditPurchase.visibility = View.GONE
                } else {
                    binding.actionDeletePurchase.visibility = View.VISIBLE
                    binding.actionEditPurchase.visibility = View.VISIBLE
                    binding.actionDeletePurchase.setOnClickListener { presenter.removePurchase(holder.adapterPosition) }
                    binding.actionEditPurchase.setOnClickListener { presenter.editPurchaseData(holder.adapterPosition) }
                }
                if(visiblePurchases.contains(contribution.purchases[position])){
                    binding.purchaseContent.visibility = View.VISIBLE
                } else {
                    binding.purchaseContent.visibility = View.GONE
                }
                binding.mainPurchaseContent.setOnClickListener { presenter.collapsePurchaseData(holder.adapterPosition)}
                binding.hidePurchaseContentButton.setOnClickListener { presenter.collapsePurchaseData(holder.adapterPosition)}

            } else {
                binding.itemPurchaseId.visibility = View.INVISIBLE
            }
        }

        override fun getItemCount(): Int = contribution.purchases.size + 1

        inner class ViewHolder(val binding: ItemPurchaseBinding) : RecyclerView.ViewHolder(binding.root)

        fun setVisible(position: Int) {
            visiblePurchases.add(contribution.purchases[position])
            notifyItemChanged(position)
        }

        fun setGone(position: Int) {
            visiblePurchases.remove(contribution.purchases[position])
            notifyItemChanged(position)
        }
    }

    inner private class ChargesAdapter(var purchase: Purchase) : RecyclerView.Adapter<ChargesAdapter.ViewHolder>() {

//        init {
//            setHasStableIds(true)
//        }
//
//        override fun getItemId(position: Int): Long {
//            return purchase.charges[position].hashCode().toLong()
//        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemChargeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_charge, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            binding.charge = purchase.charges[position]
            binding.friendInCharge = purchase.charges[position].charged?.friend
        }

        override fun getItemCount(): Int = purchase.charges.size

        inner class ViewHolder(val binding: ItemChargeBinding) : RecyclerView.ViewHolder(binding.root)
    }

}


