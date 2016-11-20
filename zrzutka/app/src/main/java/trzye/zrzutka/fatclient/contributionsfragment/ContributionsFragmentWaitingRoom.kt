package trzye.zrzutka.fatclient.contributionsfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom

object ContributionsFragmentWaitingRoom : PresentersWaitingRoom<ContributionsFragmentContract.Presenter>(){
    override fun initPresenter(): ContributionsFragmentContract.Presenter {
        return ContributionsFragmentPresenter()
    }
}