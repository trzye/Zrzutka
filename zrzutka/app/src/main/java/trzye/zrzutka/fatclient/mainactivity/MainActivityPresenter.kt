package trzye.zrzutka.fatclient.mainactivity

import trzye.zrzutka.fatclient.mainactivity.MainActivityContract.Presenter
import trzye.zrzutka.fatclient.mainactivity.MainActivityContract.View


class MainActivityPresenter : Presenter() {

    override fun showContributions() {
        view.showContributionsFragmentView()
        view.hideMenu()
    }

    override fun dismissView() {
        view.dismissView()
    }

    override fun showFriends() {
        view.showFriendsFragmentView()
        view.hideMenu()
    }

}

