package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract.Presenter
import trzye.zrzutka.model.ModelProvider
import trzye.zrzutka.mvp.PresentersWaitingRoom

object ContributionActivityWaitingRoom : PresentersWaitingRoom<Presenter>() {

    override fun initPresenter(): Presenter {
        return ContributionActivityPresenter(ModelProvider.databaseService)
    }

}