package pl.edu.pw.jereczem.zrzutka.client

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ContributionFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.content_contribution, null)
        val contributorsLabel = view.resources.getString(R.string.tab_contributors)
        val summaryLabel = view.resources.getString(R.string.tab_summary)
        val purchasesLabel = view.resources.getString(R.string.tab_purchases)

        val tabLayout = view.findViewById(R.id.tabLayout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText(summaryLabel))
        tabLayout.addTab(tabLayout.newTab().setText(purchasesLabel))
        tabLayout.addTab(tabLayout.newTab().setText(contributorsLabel))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL;

        val viewPager = view.findViewById(R.id.viewPager) as ViewPager
        val pagerAdapter = ContributionPagerAdapter(fragmentManager)

        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.setOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
            }

        })

        return view
    }


}
