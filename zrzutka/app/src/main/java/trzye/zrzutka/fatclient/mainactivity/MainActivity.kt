package trzye.zrzutka.fatclient.mainactivity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import trzye.zrzutka.R
import trzye.zrzutka.fatclient.contributionsfragment.ContributionsFragment
import trzye.zrzutka.fatclient.mainactivity.MainActivityContract.Presenter
import trzye.zrzutka.fatclient.mainactivity.MainActivityContract.View
import trzye.zrzutka.fatclient.menuactivity.AbstractMenuActivity

class MainActivity(private val parentActivity: AppCompatActivity) : AbstractMenuActivity<View, Presenter>(MainActivityWaitingRoom), View{

    lateinit var toolbar: Toolbar
    lateinit var drawer: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var navigation: NavigationView

    constructor() : this(AppCompatActivity())

    override fun startAsContributionsMainActivity(dismissOtherViews: Boolean) {
        val intent = Intent(parentActivity, this.javaClass)
        if(dismissOtherViews) waitingRoom.presenters.values.forEach { it.dismissView() }
        waitingRoom.addJobForNextPresenter(Presenter::showContributions)
        parentActivity.startActivity(intent)
    }

    override fun showContributionsFragmentView(){
        val fragment = ContributionsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
    }

    override fun getMainActivityView(): View {
        return this
    }

    override fun hideMenu() {
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigation = findViewById(R.id.view_navigation) as NavigationView
        navigation.setNavigationItemSelectedListener(this)
        super.onCreate(savedInstanceState)

    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
