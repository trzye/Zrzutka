package trzye.zrzutka.fatclient.mainactivity

import trzye.zrzutka.fatclient.menuactivity.IMenuContract


interface MainActivityContract : IMenuContract {

    interface View : IMenuContract.IMenuView<Presenter> {
        fun showContributionsFragmentView()
        fun showFriendsFragmentView()
        fun startAsContributionsMainActivity(dismissOtherViews: Boolean)
        fun hideMenu()
        fun startAsFriendsMainActivity(dismissOtherViews: Boolean)
    }

    abstract class Presenter : IMenuContract.IMenuPresenter<View>() {
        abstract fun dismissView()
    }

}
