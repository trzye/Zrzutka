package pl.edu.pw.jereczem.zrzutka.client

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.squareup.timessquare.CalendarPickerView
import pl.edu.pw.jereczem.zrzutka.client.modelaccess.DATABASE_FILENAME
import pl.edu.pw.jereczem.zrzutka.client.modelaccess.DatabaseService
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit private var dbService: DatabaseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        deleteDatabase(DATABASE_FILENAME)
        dbService = DatabaseService(this)

        val fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener { createContributionEditDialog().show() }


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)
    }

    private fun createContributionEditDialog() : Dialog {
        val dialogView = layoutInflater.inflate(R.layout.fragment_contribution_edit, null)
        val dialog = AlertDialog.Builder(this).apply {
            setView(dialogView)
            setCancelable(false)
            setOnKeyListener { dialogInterface, i, keyEvent ->
                if ((keyEvent.keyCode == KeyEvent.KEYCODE_BACK) && (keyEvent.action == KeyEvent.ACTION_UP)) {
                    AlertDialog.Builder(this.context).apply {
                        setMessage("Jesteś pewny?")
                        setPositiveButton("TAK", { di, i -> dialogInterface.dismiss() })
                        setNegativeButton("NIE", { di, i -> di.dismiss() })
                    }.show()
                }
                true
            }
            setPositiveButton("OK", { dialogInterface, i -> })
        }.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val editText = dialogView.findViewById(R.id.editText) as TextView
                if (editText.text.isEmpty())
                    Snackbar.make(dialogView, "Pusty tytuł", Snackbar.LENGTH_SHORT).setAction("Action", null).apply {
                        getView().setBackgroundColor(getColor(R.color.colorError))
                    }.show()
                else
                    dialog.dismiss()
            }
        }
        val calendarView = dialogView.findViewById(R.id.calendar_view) as CalendarPickerView
        val cMin = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.YEAR, -5)
        }
        val cMax = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.YEAR, 5)
        }
        calendarView.init(cMin.time, cMax.time).withSelectedDate(Date()).inMode(CalendarPickerView.SelectionMode.RANGE)
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dialog
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        drawer!!.closeDrawer(GravityCompat.START)

        return true
    }
}
