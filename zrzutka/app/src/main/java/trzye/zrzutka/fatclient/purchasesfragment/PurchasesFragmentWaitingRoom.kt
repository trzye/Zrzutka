package trzye.zrzutka.fatclient.contributorsfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentContract
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentPresenter
import trzye.zrzutka.fatclient.purchasesfragment.PurchasesFragmentPresenter

object PurchasesFragmentWaitingRoom : PresentersWaitingRoom<PurchasesFragmentContract.Presenter>() {

    override fun initPresenter(): PurchasesFragmentContract.Presenter {
        return PurchasesFragmentPresenter()
    }

}