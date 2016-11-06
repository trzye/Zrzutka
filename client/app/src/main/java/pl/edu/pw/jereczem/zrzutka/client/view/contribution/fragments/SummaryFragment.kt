package pl.edu.pw.jereczem.zrzutka.client

import android.util.Log
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ContributionEditableFragment

class SummaryFragment : ContributionEditableFragment() {

    override fun refresh() {
        Log.d("D/Zrzutka", "Summary refreshed")
    }

    override val layoutId = R.layout.summary_fragment
    override val labelId = R.string.tab_summary
}