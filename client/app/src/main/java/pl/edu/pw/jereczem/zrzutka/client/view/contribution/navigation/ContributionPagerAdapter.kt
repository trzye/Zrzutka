package pl.edu.pw.jereczem.zrzutka.client.view.contribution.navigation

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment

class ContributionPagerAdapter(fragmentManager: FragmentManager,val  fragments: List<ContributionEditableFragment>) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): ContributionEditableFragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
