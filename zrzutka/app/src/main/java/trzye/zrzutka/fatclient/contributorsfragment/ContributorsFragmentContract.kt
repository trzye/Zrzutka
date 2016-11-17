package trzye.zrzutka.fatclient.contributionactivity

import trzye.zrzutka.mvp.IContract

interface ContributorsFragmentContract : IContract{

    interface Presenter : IContract.IPresenter<View>

    interface View : IContract.IView<Presenter>

}