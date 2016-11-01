package pl.edu.pw.jereczem.zrzutka.client.view.contribution

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class ContributionEditableFragment : Fragment(){
    abstract val labelId: Int
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(layoutId, null)
        return view
    }
}
