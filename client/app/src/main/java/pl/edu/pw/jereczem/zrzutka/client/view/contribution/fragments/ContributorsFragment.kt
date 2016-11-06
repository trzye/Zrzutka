package pl.edu.pw.jereczem.zrzutka.client

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import pl.edu.pw.jereczem.zrzutka.client.controller.ActualManagedContribution
import pl.edu.pw.jereczem.zrzutka.client.controller.setColor
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contributor
import pl.edu.pw.jereczem.zrzutka.client.model.friend.Friend
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment

class ContributorsFragment : ContributionEditableFragment() {
    override val layoutId = R.layout.contributors_fragment
    override val labelId = R.string.tab_contributors
    lateinit private var adapter: ContributorsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val contributorsRecyclerView = view.findViewById(R.id.contributorsListView) as RecyclerView
        contributorsRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        contributorsRecyclerView.layoutManager = layoutManager

        adapter = ContributorsAdapter(ActualManagedContribution.contribution.contributors.reversed().toMutableList(), contributorsRecyclerView)
        contributorsRecyclerView.adapter = adapter

        val fab = view.findViewById(R.id.fab) as FloatingActionButton?

        fab!!.setOnClickListener {
            val friend = Friend("Test")
            val contributor = Contributor(friend = friend)
            adapter.addContributor(contributor)
            contributorsRecyclerView.scrollToPosition(0)
        }

        return view
    }

    override fun onPause() {
        val contribution = ActualManagedContribution.contribution
        val actualContributors = ActualManagedContribution.contribution.contributors
        val modifiedContributors = adapter.contributors
        val toDelete = actualContributors.filterNot { c -> modifiedContributors.contains(c) }
        val toAdd = modifiedContributors.filterNot { c -> actualContributors.contains(c) }
        toDelete.forEach { contribution.removeContributor(it) }
        toAdd.forEach { if (it != null) contribution.addContributor(it) }
        super.onStop()
    }

}

private class ContributorsAdapter(val contributors: MutableList<Contributor?>, val recyclerView: RecyclerView) : RecyclerView.Adapter<ContributorsAdapter.ViewHolder>() {

    init {
        contributors.add(null)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contributor = contributors[position]
        if(contributor != null) {
            holder.contributorName.text = contributor.friend.getShowingName()
            holder.contributorInitials.text = contributor.friend.getShowingName().getInitials()
            holder.contributorCircle.setImageResource(setColor(contributor.friend.colorId))
            holder.contributorsRemove.visibility = View.VISIBLE
            holder.layout.visibility = View.VISIBLE
        } else {
            holder.layout.visibility = View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.contributor_list_item, parent, false)
        return ViewHolder(view, this)
    }

    override fun getItemCount() = contributors.size

    private fun String.getInitials(): String {
        val splitted = split(" ")
        var initials = splitted.first().toUpperCase().getOrElse(0, { ' ' }).toString()
        if (splitted.size > 1)
            initials += splitted[1].toUpperCase().first()
        return initials
    }

    class ViewHolder(
            val view: View,
            val contributorsAdapter: ContributorsAdapter
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val contributorName = view.findViewById(R.id.contributorsName) as TextView
        val contributorInitials = view.findViewById(R.id.contributorInitials) as TextView
        val contributorCircle = view.findViewById(R.id.contributorCircle) as CircleImageView
        val contributorsRemove: View = view.findViewById(R.id.contributorsRemove)
        val layout: View = view.findViewById(R.id.item_contributor_id)

        init {
            contributorsRemove.setOnClickListener(this)
        }


        override fun onClick(v: View) {
            val position = adapterPosition
            val deleted = contributorsAdapter.remove(position)
            Snackbar.make(v, R.string.contributor_deleted, Snackbar.LENGTH_SHORT).apply {
                setAction(R.string.undo, {
                    contributorsAdapter.addContributor(position, deleted)
                })
            }.show()
            contributorsRemove.visibility = View.INVISIBLE
        }

    }

    fun remove(position: Int): Contributor {
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, contributors.size)
        return contributors.removeAt(position)!!
    }

    fun addContributor(position: Int, contributor: Contributor) {
        contributors.add(position, contributor)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, contributors.size)
        if (position == 0)
            recyclerView.scrollToPosition(0)
    }

    fun addContributor(contributor: Contributor) {
        addContributor(0, contributor)
    }

}




