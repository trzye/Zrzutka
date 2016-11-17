package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.mvp.IContract

interface PurchasesFragmentContract : IContract{

    interface Presenter : IContract.IPresenter<View>

    interface View : IContract.IView<Presenter>

}