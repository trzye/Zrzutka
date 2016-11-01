package pl.edu.pw.jereczem.zrzutka.client.view.common

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.Context
import pl.edu.pw.jereczem.zrzutka.client.R

object AlertDialogs {

    fun backPressedAlert(context: Context, onYes: () -> Unit, onNo: () -> Unit): AlertDialog
            = Builder(context)
            .setMessage(R.string.alert_dialog_back_pressed_title)
            .setPositiveButton(R.string.dialog_yes, { di, i -> onYes() })
            .setNegativeButton(R.string.dialog_no, { di, i -> onNo() })
            .create()

}
