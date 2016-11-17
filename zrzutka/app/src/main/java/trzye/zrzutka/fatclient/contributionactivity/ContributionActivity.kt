package trzye.zrzutka.fatclient.contributionactivity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import trzye.zrzutka.R
import trzye.zrzutka.databinding.ActivityContributionBinding
import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract.Presenter
import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract.View
import trzye.zrzutka.fatclient.mainactivity.MainActivity
import trzye.zrzutka.fatclient.mainactivity.MainActivityContract
import trzye.zrzutka.model.entity.Contribution
import trzye.zrzutka.mvp.AbstractMenuActivity

class ContributionActivity(private val parentActivity: Activity) : AbstractMenuActivity<View, Presenter>(ContributionActivityWaitingRoom), ContributionActivityContract.View{

    constructor() : this(AppCompatActivity())

    private lateinit var binding: ActivityContributionBinding
    lateinit var drawer: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var navigation: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contribution)

        drawer = findViewById(R.id.activity_contribution) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigation = findViewById(R.id.view_navigation) as NavigationView
        navigation.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.bindData()
    }

    override fun startAsEditableContributionActivity(contributionId : Long) {
        val intent = Intent(parentActivity, this.javaClass)
        waitingRoom.addJobForNextPresenter({Presenter::editContribution.invoke(it, contributionId)})
        parentActivity.startActivity(intent)
    }

    override fun getMainActivityView(): MainActivityContract.View {
        return MainActivity(this)
    }

    override fun bindData(contribution: Contribution) {
        binding.contribution = contribution
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
    }

    override fun getContributorsFragmentView(): ContributorsFragmentContract.View {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPurchasesFragmentView(): PurchasesFragmentContract.View {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSummaryFragmentView(): SummaryFragmentContract.View {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
