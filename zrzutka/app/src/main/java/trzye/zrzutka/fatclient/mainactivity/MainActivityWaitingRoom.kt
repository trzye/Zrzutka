package trzye.zrzutka.fatclient.mainactivity

import trzye.zrzutka.androidmvp.PresentersWaitingRoom

object MainActivityWaitingRoom : PresentersWaitingRoom<MainActivityContract.Presenter>() {
    override fun initPresenter(): MainActivityPresenter {
        return MainActivityPresenter()
    }
}