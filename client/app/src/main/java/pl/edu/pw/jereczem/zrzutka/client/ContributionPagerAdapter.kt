package pl.edu.pw.jereczem.zrzutka.client

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ContributionPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return MainFragment()
    }

    override fun getCount(): Int {
        return 3
    }

}
