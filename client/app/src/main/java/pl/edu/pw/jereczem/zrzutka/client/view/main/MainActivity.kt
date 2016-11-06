package pl.edu.pw.jereczem.zrzutka.client.view.main

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import pl.edu.pw.jereczem.zrzutka.client.ContributionMainFragment
import pl.edu.pw.jereczem.zrzutka.client.R
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contribution
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contributor
import pl.edu.pw.jereczem.zrzutka.client.model.friend.Friend
import pl.edu.pw.jereczem.zrzutka.client.modelaccess.DATABASE_FILENAME
import pl.edu.pw.jereczem.zrzutka.client.modelaccess.DatabaseService
import pl.edu.pw.jereczem.zrzutka.client.controller.ActualManagedContribution

const val ACTUAL_FRAGMENT_KEY = "ACTUAL_FRAGMENT_KEY"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit private var dbService: DatabaseService
    lateinit var fragmentChanger: FragmentChanger
    lateinit var actionBar: ActionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.appBarMainToolbar) as Toolbar
        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!

        deleteDatabase(DATABASE_FILENAME)
        dbService = DatabaseService(this)
        fragmentChanger = FragmentChanger(supportFragmentManager, findViewById(R.id.nav_view) as NavigationView, this)
        fragmentChanger.actualState = STATE.values()[savedInstanceState?.getInt(ACTUAL_FRAGMENT_KEY) ?: 0]

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)

    }

    override fun onResume() {
        when(fragmentChanger.actualState){
            STATE.MainFragment -> fragmentChanger.changeToMainFragment()
            STATE.ContributionFragment -> fragmentChanger.changeToContributionFragment()
        }
        super.onResume()
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(ACTUAL_FRAGMENT_KEY, fragmentChanger.actualState.ordinal)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            fragmentChanger.changeToMainFragment()
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

    companion object {
        class FragmentChanger(val fragmentManager: FragmentManager, val navigationView: NavigationView, activity: AppCompatActivity){

            val toolbarManager = ToolbarManager(activity)
            var actualState = STATE.MainFragment

            private fun changeFragment(fragment: Fragment) {
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()
            }

            fun changeToMainFragment() {
                actualState = STATE.MainFragment
                toolbarManager.setupForMainFragment()
                changeFragment(MainFragment())
            }
            fun changeToContributionFragment() {
                actualState = STATE.ContributionFragment
                toolbarManager.setupForContribution()
                changeFragment(ContributionMainFragment())
                uncheckMenuItems()
            }

            private fun uncheckMenuItems() {
                for (i in 0..navigationView.menu.size() - 1)
                    navigationView.menu.getItem(i).isChecked = false
                navigationView.setCheckedItem(R.id.nav_none)
            }

        }
    }

    enum class STATE {
        MainFragment,
        ContributionFragment
    }
}





