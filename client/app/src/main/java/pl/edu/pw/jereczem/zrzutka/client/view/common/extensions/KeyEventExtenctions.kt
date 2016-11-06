package pl.edu.pw.jereczem.zrzutka.client.view.common.extensions

import android.view.KeyEvent

fun KeyEvent.isBackPressed() = (keyCode == KeyEvent.KEYCODE_BACK) && (action == KeyEvent.ACTION_UP)