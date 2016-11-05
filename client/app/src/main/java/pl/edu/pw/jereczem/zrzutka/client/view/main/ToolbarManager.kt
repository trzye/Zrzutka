package pl.edu.pw.jereczem.zrzutka.client.view.main

import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.design.widget.CoordinatorLayout.LayoutParams
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import pl.edu.pw.jereczem.zrzutka.client.R
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contribution
import pl.edu.pw.jereczem.zrzutka.client.view.common.AlertDialogs
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.dialogs.createContributionEditDialog
import java.text.SimpleDateFormat
import java.util.*

class ToolbarManager(val activity: Activity){

    val dateFormat = SimpleDateFormat("dd.MM.yyy", Locale.getDefault())
    val toolbar = activity.findViewById(R.id.appBarMainToolbar) as Toolbar

    fun setupForMainFragment(){
        toolbar.title = activity.getString(R.string.app_name)
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
        toolbar.overflowIcon = activity.getDrawable(R.drawable.ic_more_vert_white_24dp)
        toolbar.subtitle = ""
        toolbar.isClickable = false
    }

    fun setupForContribution(contribution: Contribution, editable: Boolean = true){
        toolbar.title = contribution.title
        toolbar.subtitle = createSubtitle(contribution)
        if(editable){
            toolbar.setOnClickListener {
                createContributionEditDialog(
                        activity,
                        contribution,
                        {
                            toolbar.title = contribution.title
                            toolbar.subtitle =  createSubtitle(contribution)
                        }
                ).show()
            }
        } else {
            toolbar.isClickable = false
        }
    }

    private fun createSubtitle(contribution: Contribution): String {
        return if (contribution.startDate.hasSameYYMMDDas(contribution.endDate))
            dateFormat.format(contribution.startDate)
        else
            "${dateFormat.format(contribution.startDate)} - ${dateFormat.format(contribution.endDate)}"
    }


}

private fun Date.hasSameYYMMDDas(date: Date): Boolean {
    val dateFormat = SimpleDateFormat("dd-MM-yyy", Locale.getDefault())
    return dateFormat.format(date) == dateFormat.format(this)
}

