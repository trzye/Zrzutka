package trzye.zrzutka.fatclient.menuactivity

import android.app.AlertDialog
import android.support.design.widget.NavigationView
import android.view.MenuItem
import trzye.zrzutka.R
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
            R.id.menu_about -> { AlertDialog.Builder(this).setTitle("O Aplikacji").setMessage("Tutaj będzie coś więcej").create().show()}
            R.id.menu_friends_list -> { presenter.showFriends() }
            else -> {}
        }

        return true
    }

}
