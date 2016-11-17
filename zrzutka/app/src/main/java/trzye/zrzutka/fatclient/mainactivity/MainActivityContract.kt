package trzye.zrzutka.fatclient.mainactivity

import trzye.zrzutka.mvp.IMenuContract


interface MainActivityContract : IMenuContract {

    interface View : IMenuContract.IMenuView<Presenter> {
        fun showContributionsFragmentView()
        fun startAsContributionsMainActivity()
        fun hideMenu()
    }

    interface Presenter : IMenuContract.IMenuPresenter<View>

}
