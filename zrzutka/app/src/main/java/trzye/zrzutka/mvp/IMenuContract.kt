package trzye.zrzutka.mvp

import trzye.zrzutka.fatclient.mainactivity.MainActivityContract

interface IMenuContract : IContract{
    interface IMenuView<out P : IMenuPresenter<*>> : IContract.IView<P>{
        fun getMainActivityView() : MainActivityContract.View
    }
    interface IMenuPresenter<V : IMenuView<IMenuPresenter<V>>> : IContract.IPresenter<V>{
        fun showContributions()
    }
}
