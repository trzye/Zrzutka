package trzye.zrzutka.fatclient.purchasesfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom
import trzye.zrzutka.fatclient.contributorsfragment.PurchasesFragmentContract

object PurchasesFragmentWaitingRoom : PresentersWaitingRoom<PurchasesFragmentContract.Presenter>() {

    override fun initPresenter(): PurchasesFragmentContract.Presenter {
        return PurchasesFragmentPresenter()
    }

}