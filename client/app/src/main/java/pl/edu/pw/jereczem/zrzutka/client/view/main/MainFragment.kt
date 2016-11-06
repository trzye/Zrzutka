package pl.edu.pw.jereczem.zrzutka.client.view.main

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.pw.jereczem.zrzutka.client.R
import pl.edu.pw.jereczem.zrzutka.client.controller.ActualManagedContribution
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contribution
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.dialogs.createContributionEditDialog

class MainFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.content_main, null)
        val fab = view.findViewById(R.id.fab) as FloatingActionButton?

        fab!!.setOnClickListener {
            val initContribution = Contribution("")
            createContributionEditDialog(
                    this.context,
                    initContribution,
                    {
                        ActualManagedContribution.position = 0
                        ActualManagedContribution.contribution = initContribution
                        (activity as MainActivity).fragmentChanger.changeToContributionFragment()
                    }
            ).show()
        }
        return view
    }

}
