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
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contributor
import pl.edu.pw.jereczem.zrzutka.client.model.friend.Friend
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment

class ContributorsFragment : ContributionEditableFragment() {
    override val layoutId = R.layout.contributors_fragment
    override val labelId = R.string.tab_contributors
    lateinit private var adapter: ContributorsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val contributorsRecyclerView = setAdapter(view)
        setActionButton(contributorsRecyclerView, view)
        return view
    }

    private fun setActionButton(contributorsRecyclerView: RecyclerView, view: View) {
        val fab = view.findViewById(R.id.fab) as FloatingActionButton?

        fab!!.setOnClickListener {
            adapter.addContributor(Contributor(Friend("Testowy")))
            contributorsRecyclerView.scrollToPosition(0)
        }
    }

    private fun setAdapter(view: View): RecyclerView {
        val contributorsRecyclerView = view.findViewById(R.id.contributorsListView) as RecyclerView
        contributorsRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        contributorsRecyclerView.layoutManager = layoutManager

        adapter = ContributorsAdapter(ActualManagedContribution.contribution.contributors.reversed().toMutableList(), contributorsRecyclerView)
        contributorsRecyclerView.adapter = adapter
        return contributorsRecyclerView
    }

    override fun refresh() {
        Log.d("D/Zrzutka", "Contributors refreshed")
        if(view != null) {
            setAdapter(view!!)
        }
    }

}




