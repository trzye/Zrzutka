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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_contribution_list -> { presenter.showContributions() }
            R.id.menu_about -> { }
            R.id.menu_friends_list -> {}
            else -> {}
        }

        return true
    }

}
