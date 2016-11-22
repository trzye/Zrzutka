package trzye.zrzutka.fatclient.contributionfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.androidmvp.AbstractFragment
import trzye.zrzutka.androidmvp.PresentersWaitingRoom


abstract class AbstractContributionFragment<V : IContributionContract.IContributionView<P>, P : IContributionContract.IContributionPresenter<V>>(waitingRoom: PresentersWaitingRoom<P>,val dataHolder: ContributionDataHolder?)
    : AbstractFragment<V, P>(waitingRoom), IContributionContract.IContributionView<P>{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(dataHolder != null){
            presenter.init(dataHolder)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    var isStarted = false

    override fun onStart() {
        super.onStart()
        presenter.attachView(this as V)
        isStarted = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if(isVisibleToUser && isStarted){
            presenter.updateView(this as V)
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    abstract val labelId: Int

}