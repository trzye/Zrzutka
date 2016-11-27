package trzye.zrzutka.fatclient.summaryfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom

object SummaryFragmentWaitingRoom : PresentersWaitingRoom<SummaryFragmentContract.Presenter>() {

    override fun initPresenter(): SummaryFragmentContract.Presenter {
        return SummaryFragmentPresenter()
    }

}