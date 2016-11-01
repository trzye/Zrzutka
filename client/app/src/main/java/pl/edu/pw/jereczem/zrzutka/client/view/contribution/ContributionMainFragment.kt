package pl.edu.pw.jereczem.zrzutka.client

 import android.os.Bundle
 import android.support.design.widget.TabLayout
 import android.support.v4.app.Fragment
 import android.support.v4.view.ViewPager
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment
 import pl.edu.pw.jereczem.zrzutka.client.view.contribution.navigation.ContributionPagerAdapter
 import pl.edu.pw.jereczem.zrzutka.client.view.main.ToolbarManager

class ContributionMainFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.content_contribution, null)
        val fragments = listOf(ContributorsFragment(), PurchasesFragment(), SummaryFragment())
        val viewPager = viewPagerSetup(fragments, view)
        val tabLayout = tabLayoutSetup(fragments, view, viewPager)
        return view
    }

    private fun viewPagerSetup(fragments: List<ContributionEditableFragment>, view: View): ViewPager {
        val viewPager = view.findViewById(R.id.viewPager) as ViewPager
        val pagerAdapter = ContributionPagerAdapter(fragmentManager, fragments)
        viewPager.adapter = pagerAdapter
        return viewPager
    }

    private fun tabLayoutSetup(fragments: List<ContributionEditableFragment>, view: View, viewPager: ViewPager): TabLayout {
        return (view.findViewById(R.id.tabLayout) as TabLayout).apply {
            tabGravity = TabLayout.GRAVITY_FILL
            tabLabelsSetup(fragments, view)
            onTabSelectedSetup(viewPager)
        }
    }

    private fun TabLayout.tabLabelsSetup(fragments: List<ContributionEditableFragment>, view: View) {
        fragments.forEach {
            fragment ->
            addTab(newTab().setText(view.resources.getString(fragment.labelId)))
        }
    }

    private fun TabLayout.onTabSelectedSetup(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(this))
        setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) { }
            override fun onTabSelected(tab: TabLayout.Tab) { }
        })
    }
}
