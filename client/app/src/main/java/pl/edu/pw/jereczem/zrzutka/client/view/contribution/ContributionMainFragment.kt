package pl.edu.pw.jereczem.zrzutka.client

 import android.os.Bundle
 import android.support.design.widget.TabLayout
 import android.support.v4.app.Fragment
 import android.support.v4.view.ViewPager
 import android.util.Log
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import pl.edu.pw.jereczem.zrzutka.client.controller.ActualManagedContribution
 import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment
 import pl.edu.pw.jereczem.zrzutka.client.view.contribution.navigation.ContributionPagerAdapter

class ContributionMainFragment : Fragment(){

    lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.content_contribution, null)
            val fragments = listOf(ContributorsFragment(), PurchasesFragment(), SummaryFragment())
            val viewPager = viewPagerSetup(fragments, view)
            tabLayout = tabLayoutSetup(fragments, view, viewPager)
            tabLayout.getTabAt(ActualManagedContribution.position)!!.select()
        return view
    }


    override fun onPause() {
        onSaveInstanceState(Bundle())
        super.onPause()
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
            onTabSelectedSetup(viewPager)
            setupWithViewPager(viewPager)
            tabLabelsSetup(fragments)
        }
    }

    private fun TabLayout.tabLabelsSetup(fragments: List<ContributionEditableFragment>) {
        fragments.forEachIndexed { i, fragment ->
            getTabAt(i)?.setText(fragment.labelId)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        ActualManagedContribution.position = tabLayout.selectedTabPosition
    }

    private fun TabLayout.onTabSelectedSetup(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(this))
        setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                viewPager.adapter.count
            }
            override fun onTabUnselected(tab: TabLayout.Tab) { }
            override fun onTabSelected(tab: TabLayout.Tab) { }
        })
    }


}
