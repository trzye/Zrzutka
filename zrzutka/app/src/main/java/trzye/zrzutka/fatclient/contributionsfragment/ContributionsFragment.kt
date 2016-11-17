package trzye.zrzutka.fatclient.contributionsfragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.R
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialog
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract

class ContributionsFragment() : Fragment(), ContributionsFragmentContract.View {

    override var presenterId: Long = 1L
    override val presenter: ContributionsFragmentContract.Presenter = ContributionsFragmentPresenter()

    override fun dismissView() {
        activity.finish()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contributions, null)
        val actionButton = view.findViewById(R.id.actionButton) as FloatingActionButton
        presenter.attachView(this)
        actionButton.setOnClickListener { presenter.createNewContribution() }
        return view
    }

    override fun getContributionDialogView(): ContributionDialogContract.View {
        return ContributionDialog(activity)
    }
}