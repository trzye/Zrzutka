package pl.edu.pw.jereczem.zrzutka.client

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.pw.jereczem.zrzutka.client.controller.ActualManagedContribution
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Purchase
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment

class PurchasesFragment : ContributionEditableFragment() {
    override val layoutId = R.layout.purchases_fragment
    override val labelId = R.string.tab_purchases
    lateinit private var adapter: PurchasesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val purchasesRecyclerView = setAdapter(view)
        setActionButton(purchasesRecyclerView, view)
        return view
    }

    private fun setActionButton(purchasesRecyclerView: RecyclerView, view: View) {
        val fab = view.findViewById(R.id.fab) as FloatingActionButton?

        fab!!.setOnClickListener {
            val purchase = Purchase("TEST")
            adapter.addPurchase(purchase)
            purchasesRecyclerView.scrollToPosition(0)
        }
    }

    private fun setAdapter(view: View): RecyclerView {
        val purchasesRecyclerView = view.findViewById(R.id.purchasesRecyclerView) as RecyclerView
        purchasesRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        purchasesRecyclerView.layoutManager = layoutManager

        adapter = PurchasesAdapter(ActualManagedContribution.contribution.purchases.reversed().toMutableList(), purchasesRecyclerView)
        purchasesRecyclerView.adapter = adapter
        purchasesRecyclerView.setHasFixedSize(true);
        return purchasesRecyclerView
    }

    override fun refresh() {
        Log.d("D/Zrzutka", "Purchase refreshed")
        if (view != null) setAdapter(view!!)
    }

}

