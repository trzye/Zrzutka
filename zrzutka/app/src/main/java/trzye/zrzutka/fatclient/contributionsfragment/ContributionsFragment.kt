package trzye.zrzutka.fatclient.contributionsfragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.R
import trzye.zrzutka.androidmvp.AbstractFragment
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialog
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.fatclient.contributionsfragment.ContributionsFragmentContract.Presenter

class ContributionsFragment() : AbstractFragment<ContributionsFragmentContract.View, Presenter>(ContributionsFragmentWaitingRoom), ContributionsFragmentContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater,container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_contributions, null)
        val actionButton = view.findViewById(R.id.actionButton) as FloatingActionButton
        actionButton.setOnClickListener { presenter.createNewContribution() }
        return view
    }

    override fun getContributionDialogView(): ContributionDialogContract.View {
        return ContributionDialog(activity)
    }
}

