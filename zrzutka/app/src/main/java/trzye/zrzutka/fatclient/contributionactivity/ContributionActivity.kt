package trzye.zrzutka.fatclient.contributionactivity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.DragEvent
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_contribution.*
import trzye.zrzutka.R
import trzye.zrzutka.databinding.ActivityContributionBinding
import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract.Presenter
import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract.View
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialog
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.fatclient.contributionfragment.AbstractContributionFragment
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.fatclient.contributionsfragment.ContributionsFragment
import trzye.zrzutka.fatclient.contributionsfragment.ContributionsFragmentContract
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragment
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentContract
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentWaitingRoom
import trzye.zrzutka.fatclient.purchasesfragment.PurchasesFragment
import trzye.zrzutka.fatclient.mainactivity.MainActivity
import trzye.zrzutka.fatclient.mainactivity.MainActivityContract
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.fatclient.menuactivity.AbstractMenuActivity

class ContributionActivity(private val parentActivity: Activity) : AbstractMenuActivity<View, Presenter>(ContributionActivityWaitingRoom), ContributionActivityContract.View {

    constructor() : this(AppCompatActivity())

    private lateinit var binding: ActivityContributionBinding
    lateinit var drawer: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var navigation: NavigationView
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var fragments: MutableList<AbstractContributionFragment<*, *>>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contribution)
        binding.toolbar.inflateMenu(R.menu.activity_contribution_menu)


        super.onCreate(savedInstanceState)


        drawer = findViewById(R.id.activity_contribution) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigation = findViewById(R.id.view_navigation) as NavigationView
        navigation.setNavigationItemSelectedListener(this)

        tabLayout = findViewById(R.id.tabLayout) as TabLayout
        viewPager = findViewById(R.id.viewPager) as ViewPager

        fragments = mutableListOf()

        binding.toolbar.setNavigationOnClickListener { presenter.showContributions() }
        binding.toolbar.setOnClickListener { presenter.editBaseContributionData() }

        presenter.bindData()
        presenter.startDialogIfExists()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun startAsEditableContributionActivity(contributionId: Long) {
        val intent = Intent(parentActivity, this.javaClass)
        waitingRoom.addJobForNextPresenter({ Presenter::editContribution.invoke(it, contributionId) })
        parentActivity.startActivity(intent)
    }

    override fun startAsReadOnlyContributionActivity(contributionId: Long) {
        val intent = Intent(parentActivity, this.javaClass)
        waitingRoom.addJobForNextPresenter({ Presenter::readContribution.invoke(it, contributionId) })
        parentActivity.startActivity(intent)
    }

    override fun getMainActivityView(): MainActivityContract.View {
        return MainActivity(this)
    }

    override fun getContributionFragmentViews(): List<IContributionContract.IContributionView<*>> {
        return fragments
    }

    override fun setToolbarClickable() {
        binding.toolbar.isClickable = true
    }

    override fun setToolbarUnclickable() {
        binding.toolbar.isClickable = false
    }

    override fun getContributionEditDialogView(): ContributionDialogContract.View {
        return ContributionDialog(this)
    }

    override fun setEditIcon() {
    }

    override fun setReadIcon() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_edit -> {presenter.setEditMode(); Toast.makeText(this,"EDIT", Toast.LENGTH_SHORT).show() }
            R.id.menu_read -> {presenter.setReadMode(); Toast.makeText(this,"READ", Toast.LENGTH_SHORT).show() }
        }
        return true
    }

    override fun bindData(dataHolder: ContributionDataHolder) {
        binding.contribution = dataHolder.contribution
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        if(dataHolder.isEditable) {
            fragments.add(ContributorsFragment(dataHolder))
            fragments.add(PurchasesFragment(dataHolder))
        } else {
            fragments.add(PurchasesFragment(dataHolder))
            fragments.add(ContributorsFragment(dataHolder))
        }

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getCount(): Int = fragments.size
            override fun getItem(position: Int) = fragments[position]
        }

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) { }
            override fun onTabSelected(tab: TabLayout.Tab) { }
        })

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setupWithViewPager(viewPager)



        fragments.forEachIndexed { i, f ->
            tabLayout.getTabAt(i)?.text = resources.getString(f.labelId)
        }
    }

    override fun onBackPressed() {
        presenter.showContributions()
    }

}
