package trzye.zrzutka.fatclient.menuactivity

import trzye.zrzutka.fatclient.mainactivity.MainActivityContract
import trzye.zrzutka.mvp.IContract

interface IMenuContract : IContract {
    interface IMenuView<out P : IMenuPresenter<*>> : IContract.IView<P> {
        fun getMainActivityView() : MainActivityContract.View
    }
    interface IMenuPresenter<V : IMenuView<IMenuPresenter<V>>> : IContract.IPresenter<V> {
        fun showContributions()
    }
}
