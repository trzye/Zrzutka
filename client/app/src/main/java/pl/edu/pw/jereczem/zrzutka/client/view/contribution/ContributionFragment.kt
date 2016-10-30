package pl.edu.pw.jereczem.zrzutka.client

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost

class ContributionFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.content_contribution, null)
        val contributorsLabel = view.resources.getString(R.string.tab_contributors)
        val summaryLabel = view.resources.getString(R.string.tab_summary)
        val purchasesLabel = view.resources.getString(R.string.tab_purchases)

        val tabHost = (view.findViewById(R.id.contributionTabHost) as TabHost).apply {
            setup()
        }

        tabHost.addTab(summaryLabel, R.id.summaryTab)
        tabHost.addTab(contributorsLabel, R.id.contributorsTab)
        tabHost.addTab(purchasesLabel, R.id.purchasesTab)

        return view
    }

    private fun TabHost.addTab(contributorsLabel: String, layoutId: Int) {
        addTab(
                newTabSpec(contributorsLabel).apply {
                    setContent(layoutId)
                    setIndicator(contributorsLabel)
                }
        )
    }

}
