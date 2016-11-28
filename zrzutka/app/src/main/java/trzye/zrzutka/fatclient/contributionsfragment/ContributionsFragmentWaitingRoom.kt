package trzye.zrzutka.fatclient.contributionsfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom
import trzye.zrzutka.model.ModelProvider

object ContributionsFragmentWaitingRoom : PresentersWaitingRoom<ContributionsFragmentContract.Presenter>(){
    override fun initPresenter(): ContributionsFragmentContract.Presenter {
        return ContributionsFragmentPresenter(ModelProvider.databaseService)
    }
}