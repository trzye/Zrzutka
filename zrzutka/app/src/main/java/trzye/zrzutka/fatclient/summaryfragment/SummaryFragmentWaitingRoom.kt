package trzye.zrzutka.fatclient.summaryfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom
import trzye.zrzutka.model.ModelProvider

object SummaryFragmentWaitingRoom : PresentersWaitingRoom<SummaryFragmentContract.Presenter>() {

    override fun initPresenter(): SummaryFragmentContract.Presenter {
        return SummaryFragmentPresenter(ModelProvider.databaseService)
    }

}