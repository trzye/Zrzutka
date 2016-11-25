package trzye.zrzutka.fatclient.menuactivity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.view.MenuItem
import trzye.zrzutka.R
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialog
import trzye.zrzutka.androidmvp.AbstractActivity
import trzye.zrzutka.androidmvp.PresentersWaitingRoom


abstract class AbstractMenuActivity<V : IMenuContract.IMenuView<P>, P : IMenuContract.IMenuPresenter<V>>(waitingRoom: PresentersWaitingRoom<P>)
    : AbstractActivity<V, P>(waitingRoom),
        IMenuContract.IMenuView<P>,
        NavigationView.OnNavigationItemSelectedListener
{
//
//    private val PRESENTER_ID = "PRESENTER_ID"
//
//    override var presenterId: Long = waitingRoom.generateId()
//
//    override val presenter: P by lazy {
//       waitingRoom.getPresenter(presenterId)
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        presenterId = savedInstanceState?.getLong(PRESENTER_ID) ?: presenterId
//        presenter.attachView(this as V)
//    }

//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        outState?.putLong(PRESENTER_ID, presenterId)
//    }

//    override fun dismissView() {
//        waitingRoom.removePresenter(presenterId)
//        finish()
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_contribution_list -> { presenter.showContributions() }
            R.id.menu_about -> { ContributionDialog(this).startAsCreateNewContributionDialog()}
            R.id.menu_friends_list -> {}
            else -> {}
        }

        return true
    }

}
