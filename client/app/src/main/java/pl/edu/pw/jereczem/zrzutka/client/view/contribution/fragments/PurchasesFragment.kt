package pl.edu.pw.jereczem.zrzutka.client

import android.animation.LayoutTransition
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Charge
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contribution
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contributor
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Purchase
import pl.edu.pw.jereczem.zrzutka.client.model.friend.Friend
import pl.edu.pw.jereczem.zrzutka.client.view.common.extensions.toReadablePriceString
import pl.edu.pw.jereczem.zrzutka.client.view.common.setColor
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

        val contribution = Contribution("Jakiś zakup").apply {
            addContributor(Contributor(Friend("Maciek")))
            addContributor(Contributor(Friend("Andrzej")))
            addPurchase(Purchase("Prezent 1", 120.0))
            addContributor(Contributor(Friend("Wojtek")))
            addPurchase(Purchase("Prezent 2", 130.0))
        }

        adapter = PurchasesAdapter(contribution.purchases.reversed().toMutableList(), purchasesRecyclerView)
        purchasesRecyclerView.adapter = adapter
        purchasesRecyclerView.setHasFixedSize(true);


        val fab = view.findViewById(R.id.fab) as FloatingActionButton?

        fab!!.setOnClickListener {
            adapter.addPurchase(Purchase("Test", 100.0, contribution).apply { contribution.addPurchase(this) })
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
            holder.purchaseTitle.text = purchase.name
            holder.purchaseSubtitle.text = purchase.price.toReadablePriceString()
            holder.mainContent.background = getDrawableColor(holder, purchase)
            setChargesListAdapater(holder, purchase)
            expandHandling(holder, purchase)
        } else {
            holder.layout.visibility = View.INVISIBLE
        }
    }

    private fun setChargesListAdapater(holder: ViewHolder, purchase: Purchase) {
        val layoutManager = LinearLayoutManager(holder.view.context)
        holder.purchasesListItems.layoutManager = layoutManager
        holder.purchasesListItems.adapter = ChargeAdapter(purchase.charges)
        holder.purchasesListItems.setHasFixedSize(true)
    }

    private fun getDrawableColor(holder: ViewHolder, purchase: Purchase)
            = ResourcesCompat.getDrawable(holder.itemView.resources, setColor(purchase.colorId), null)

    private fun expandHandling(holder: ViewHolder, purchase: Purchase) {
        holder.setClickListener(openedPurchases, purchase)
        if (openedPurchases.contains(purchase)) {
            holder.purchaseContent.visibility = View.VISIBLE
            holder.expandableLayoutOfPurchase.layoutTransition = null
        } else {
            holder.purchaseContent.visibility = View.GONE
            holder.expandableLayoutOfPurchase.layoutTransition = LayoutTransition()
        }
    }

    override fun getItemCount(): Int {
        return purchases.size
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.purchaseContent.clearAnimation()
        holder.layout.clearAnimation()

    }

    class ViewHolder(val view: View,val purchasesAdapter: PurchasesAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener{

        val layout: View = view.findViewById(R.id.item_purchase_id)
        val mainContent: View = view.findViewById(R.id.mainPurchaseContent)
        val purchaseContent: View = view.findViewById(R.id.purchaseContent)
        val hidePurchaseContentButton: View = view.findViewById(R.id.hidePurchaseContentButton)
        val purchaseRemove: View = view.findViewById(R.id.actionDeletePurchase)
        val purchaseTitle = view.findViewById(R.id.purchaseTitle) as TextView
        val purchaseSubtitle = view.findViewById(R.id.purchaseSubtitle) as TextView
        val purchasesListItems = view.findViewById(R.id.purchasesListItems) as RecyclerView
        val expandableLayoutOfPurchase = view.findViewById(R.id.expandableView) as ViewGroup

        init {
            purchaseRemove.setOnClickListener(this)
        }

        fun onClick(openedPurchases: MutableList<Purchase>, purchase: Purchase) {
            if(openedPurchases.contains(purchase)) {
                openedPurchases.remove(purchase)
                purchaseContent.visibility = View.GONE
                expandableLayoutOfPurchase.layoutTransition = LayoutTransition()
            }
            else {
                openedPurchases.add(purchase)
                purchaseContent.visibility = View.VISIBLE
                expandableLayoutOfPurchase.layoutTransition = null
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

class ChargeAdapter(val charges: List<Charge>) : RecyclerView.Adapter<ChargeAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return charges.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.charge_list_item, parent, false)
        return ChargeAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val charge = charges[position]
        holder.chargeContributor.text = charge.charged?.friend?.getShowingName()
        holder.chargeContributorPaid.text = charge.amountPaid.toReadablePriceString()
        holder.chargeContributorToPay.text = charge.amountToPay.toReadablePriceString()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val chargeContributor = view.findViewById(R.id.chargeContributor) as TextView
        val chargeContributorPaid = view.findViewById(R.id.chargeContributorPaid) as TextView
        val chargeContributorToPay = view.findViewById(R.id.chargeContributorToPay) as TextView
    }

}
