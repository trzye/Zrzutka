package pl.edu.pw.jereczem.zrzutka.client.view.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
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
import pl.edu.pw.jereczem.zrzutka.client.view.contribution.ActualManagedContribution

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit private var dbService: DatabaseService
    lateinit var fragmentChanger: FragmentChanger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.appBarMainToolbar) as Toolbar?
        setSupportActionBar(toolbar)

        deleteDatabase(DATABASE_FILENAME)
        dbService = DatabaseService(this)
        fragmentChanger = FragmentChanger(supportFragmentManager, findViewById(R.id.nav_view) as NavigationView, this)
        fragmentChanger.changeToContributionFragment(Contribution("TEST").apply {
            for (i in 1..100){
                addContributor(Contributor(Friend("Friend $i"), this))
            }
        })


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)
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
        public class FragmentChanger(val fragmentManager: FragmentManager, val navigationView: NavigationView, activity: AppCompatActivity){

            val toolbarManager = ToolbarManager(activity)

            private fun changeFragment(fragment: Fragment) {
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()
            }

            fun changeToMainFragment() {
                toolbarManager.setupForMainFragment()
                changeFragment(MainFragment())
            }
            fun changeToContributionFragment(contribution: Contribution) {
                toolbarManager.setupForContribution(contribution)
                ActualManagedContribution.contribution = contribution
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
}



