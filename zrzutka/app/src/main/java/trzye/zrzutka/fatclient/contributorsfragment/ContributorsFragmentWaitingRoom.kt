package trzye.zrzutka.fatclient.contributorsfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentContract
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentPresenter
import trzye.zrzutka.model.ModelProvider

object ContributorsFragmentWaitingRoom : PresentersWaitingRoom<ContributorsFragmentContract.Presenter>() {

    override fun initPresenter(): ContributorsFragmentContract.Presenter {
        return ContributorsFragmentPresenter(ModelProvider.databaseService)
    }

}