package trzye.zrzutka.fatclient.contributionfragment

import trzye.zrzutka.mvp.IContract

interface IContributionContract : IContract {
    interface IContributionView<out P : IContributionPresenter<*>> : IContract.IView<P>

    abstract class IContributionPresenter<V : IContributionView<IContributionPresenter<V>>> : IContract.IPresenter<V>() {
        abstract fun setEditMode()
        abstract fun setReadMode()
        abstract fun init(dataHolder: ContributionDataHolder)
        abstract fun updateView(view: V)
    }
}

