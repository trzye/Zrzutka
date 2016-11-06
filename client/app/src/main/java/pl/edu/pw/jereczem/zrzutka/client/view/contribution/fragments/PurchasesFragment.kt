package pl.edu.pw.jereczem.zrzutka.client

import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.pw.jereczem.zrzutka.client.controller.ActualManagedContribution
import pl.edu.pw.jereczem.zrzutka.client.controller.setColor
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contributor
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Purchase
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment

class PurchasesFragment : ContributionEditableFragment() {
    override val layoutId = R.layout.purchases_fragment
    override val labelId = R.string.tab_purchases
    lateinit private var adapter: PurchasesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val purchasesRecyclerView = view.findViewById(R.id.purchasesRecyclerView) as RecyclerView
        purchasesRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        purchasesRecyclerView.layoutManager = layoutManager

        adapter = PurchasesAdapter(ActualManagedContribution.contribution.purchases.reversed().toMutableList(), purchasesRecyclerView)
        purchasesRecyclerView.adapter = adapter

        val fab = view.findViewById(R.id.fab) as FloatingActionButton?

        fab!!.setOnClickListener {
            val purchase = Purchase(name = "Test",price = 100.0)
            adapter.addPurchase(purchase)
            purchasesRecyclerView.scrollToPosition(0)
        }

        return view
    }

}

class PurchasesAdapter(val purchases: MutableList<Purchase?>,val recyclerView: RecyclerView) : RecyclerView.Adapter<PurchasesAdapter.ViewHolder>() {

    val openedPurchases = mutableListOf<Purchase>()

    init {
        purchases.add(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.purchase_list_item, parent, false)
        return PurchasesAdapter.ViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val purchase = purchases[position]
        if(purchase != null){
            holder.layout.visibility = View.VISIBLE
            holder.mainContent.background = ResourcesCompat.getDrawable(holder.itemView.resources, setColor(purchase.colorId), null)
            holder.setClickListener(openedPurchases, purchase)
            if(openedPurchases.contains(purchase))
                holder.purchaseContent.visibility = View.VISIBLE
            else
                holder.purchaseContent.visibility = View.GONE
        } else {
            holder.layout.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return purchases.size
    }

    class ViewHolder(val view: View,val purchasesAdapter: PurchasesAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener{

        val layout: View = view.findViewById(R.id.item_purchase_id)
        val mainContent: View = view.findViewById(R.id.mainPurchaseContent)
        val purchaseContent: View = view.findViewById(R.id.purchaseContent)
        val hidePurchaseContentButton: View = view.findViewById(R.id.hidePurchaseContentButton)
        val purchaseRemove: View = view.findViewById(R.id.actionDeletePurchase)

        init {
            purchaseRemove.setOnClickListener(this)
        }

        fun onClick(openedPurchases: MutableList<Purchase>, purchase: Purchase) {
            if(openedPurchases.contains(purchase)) {
                openedPurchases.remove(purchase)
                purchaseContent.visibility = View.GONE
            }
            else {
                openedPurchases.add(purchase)
                purchaseContent.visibility = View.VISIBLE
            }
        }

        fun setClickListener(openedPurchases: MutableList<Purchase>, purchase: Purchase) {
            mainContent.setOnClickListener{onClick(openedPurchases,purchase )}
            hidePurchaseContentButton.setOnClickListener{onClick(openedPurchases,purchase)}
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            val deleted = purchasesAdapter.remove(position)
            Snackbar.make(v, R.string.purchase_deleted, Snackbar.LENGTH_SHORT).apply {
                setAction(R.string.undo, {
                    purchasesAdapter.addPurchase(position, deleted)
                    purchaseRemove.visibility = View.VISIBLE
                })
            }.show()
            purchaseRemove.visibility = View.INVISIBLE
        }

    }

    fun remove(position: Int): Purchase {
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, purchases.size)
        return purchases.removeAt(position)!!
    }

    fun addPurchase(position: Int, purchase: Purchase) {
        purchases.add(position, purchase)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, purchases.size)
        if (position == 0)
            recyclerView.scrollToPosition(0)
    }

    fun addPurchase(purchase: Purchase) {
        addPurchase(0, purchase)
    }


}
