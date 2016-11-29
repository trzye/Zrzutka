package trzye.zrzutka.fatclient.purchasesfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom
import trzye.zrzutka.fatclient.purchasesfragment.PurchasesFragmentContract
import trzye.zrzutka.model.ModelProvider

object PurchasesFragmentWaitingRoom : PresentersWaitingRoom<PurchasesFragmentContract.Presenter>() {

    override fun initPresenter(): PurchasesFragmentContract.Presenter {
        return PurchasesFragmentPresenter(ModelProvider.databaseService)
    }

}