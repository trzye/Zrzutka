package pl.edu.pw.jereczem.zrzutka.client

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contributor
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ActualManagedContribution
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment
import java.util.*

class ContributorsFragment : ContributionEditableFragment() {
    override val layoutId = R.layout.contributors_fragment
    override val labelId = R.string.tab_contributors

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val contributorsRecyclerView = view.findViewById(R.id.contributorsListView) as RecyclerView
        contributorsRecyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        contributorsRecyclerView.layoutManager = layoutManager

        contributorsRecyclerView.adapter = ContributorsAdapter(ActualManagedContribution.contribution.contributors)

        return view
    }

}

class ContributorsAdapter(val contributors: List<Contributor>) : RecyclerView.Adapter<ContributorsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contributorName.text = contributors[position].friend.getShowingName()
        holder.contributorInitials.text = (holder.contributorName.text as String).getInitials()
        holder.contributorCircle.setImageResource(setColor(contributors[position].friend.colorId))
    }

    private fun setColor(id: Int): Int {
        return when(id){
            1 -> R.color.color1
            2 -> R.color.color2
            3 -> R.color.color3
            4 -> R.color.color4
            5 -> R.color.color5
            6 -> R.color.color6
            7 -> R.color.color7
            8 -> R.color.color8
            9 -> R.color.color9
            10 -> R.color.color10
            11 -> R.color.color11
            12 -> R.color.color12
            13 -> R.color.color13
            14 -> R.color.color14
            15 -> R.color.color15
            16 -> R.color.color16
            else -> R.color.color1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.contributor_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = contributors.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val contributorName = view.findViewById(R.id.contributorsName) as TextView
        val contributorInitials = view.findViewById(R.id.contributorInitials) as TextView
        val contributorCircle = view.findViewById(R.id.contributorCircle) as CircleImageView
    }

    fun getResourceId(view: View, pVariableName: String, resourceName: String, packageName: String)
            = view.resources.getIdentifier(pVariableName, resourceName, packageName)


}

private fun String.getInitials(): String {
    val splitted = split(" ")
    var initials = splitted.first().toUpperCase().getOrElse(0, {' '}).toString()
    if(splitted.size > 1)
        initials += splitted[1].toUpperCase().first()
    return initials
}


