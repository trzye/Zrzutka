package trzye.zrzutka.fatclient.menuactivity

import android.app.AlertDialog
import android.support.design.widget.NavigationView
import android.view.MenuItem
import android.widget.EditText
import trzye.zrzutka.R
import trzye.zrzutka.androidmvp.AbstractActivity
import trzye.zrzutka.androidmvp.PresentersWaitingRoom
import trzye.zrzutka.model.ModelProvider


abstract class AbstractMenuActivity<V : IMenuContract.IMenuView<P>, P : IMenuContract.IMenuPresenter<V>>(waitingRoom: PresentersWaitingRoom<P>)
    : AbstractActivity<V, P>(waitingRoom),
        IMenuContract.IMenuView<P>,
        NavigationView.OnNavigationItemSelectedListener
{

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_contribution_list -> { presenter.showContributions() }
            R.id.menu_about -> {
                val textView = EditText(this).apply { setText(ModelProvider.IP) }
                AlertDialog.Builder(this)
                    .setTitle("O Aplikacji")
                    .setMessage("Tutaj będzie coś więcej")
                    .setView(textView).setPositiveButton("OK", {d, i -> ModelProvider.IP = textView.text.toString()})
                    .create()
                    .show()
            }
            R.id.menu_friends_list -> { presenter.showFriends() }
            else -> {}
        }

        return true
    }

}
