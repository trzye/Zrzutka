package pl.edu.pw.jereczem.zrzutka.client.view.contribution.navigation

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.ViewGroup
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment

class ContributionPagerAdapter(fragmentManager: FragmentManager,val  fragments: List<ContributionEditableFragment>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): ContributionEditableFragment {
        fragments.forEach { it.refresh() }
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}
