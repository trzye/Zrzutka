package trzye.zrzutka.fatclient.contributionfragment

import trzye.zrzutka.mvp.IContract

interface IContributionContract : IContract {
    interface IContributionView<out P : IContributionPresenter<*>> : IContract.IView<P>

    interface IContributionPresenter<V : IContributionView<IContributionPresenter<V>>> : IContract.IPresenter<V> {
        fun setEditMode()
        fun setReadMode()
        fun init(dataHolder: ContributionDataHolder)
        fun updateView(view: V)
    }
}

