package pl.edu.pw.jereczem.zrzutka.client

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TabHost
import android.widget.TextView

class ContributionFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.content_contribution, null)
        val contributorsLabel = view.resources.getString(R.string.tab_contributors)
        val summaryLabel = view.resources.getString(R.string.tab_summary)
        val purchasesLabel = view.resources.getString(R.string.tab_purchases)

        val tabHost = (view.findViewById(R.id.contributionTabHost) as TabHost).apply {
            setup()
        }
//
//        tabHost.addTab(summaryLabel, R.id.summaryTab)
//        tabHost.addTab(contributorsLabel, R.id.contributorsTab)
//        tabHost.addTab(purchasesLabel, R.id.purchasesTab)

//        val x = createTabView(view.context, "Test")

        setupTab(tabHost, view.findViewById(R.id.contributorsTab), contributorsLabel, android.R.drawable.ic_dialog_email)
        setupTab(tabHost, view.findViewById(R.id.summaryTab), summaryLabel, android.R.drawable.ic_dialog_email)
        setupTab(tabHost, view.findViewById(R.id.purchasesTab), purchasesLabel, android.R.drawable.ic_dialog_email)


        return view
    }

    private fun TabHost.addTab(contributorsLabel: String, layoutId: Int) {
        addTab(
                newTabSpec(contributorsLabel).apply {
                    setContent(layoutId)
                    setIndicator("", ContextCompat.getDrawable(activity, android.R.drawable.ic_dialog_email))
                }
        )
    }

    private fun setupTab(mTabHost: TabHost, view: View, tag: String, icon: Int) {
        val tabview = createTabView(mTabHost.context, tag, icon)
        val setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent { view }
        mTabHost.addTab(setContent)
    }

    private fun createTabView(context: Context, text: String, icon: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null)
        val tv = view.findViewById(R.id.tabsText) as TextView
        val im = view.findViewById(R.id.tabsImage) as ImageView
        im.setImageDrawable(ContextCompat.getDrawable(context, icon))
        tv.text = text
        return view
    }

}
