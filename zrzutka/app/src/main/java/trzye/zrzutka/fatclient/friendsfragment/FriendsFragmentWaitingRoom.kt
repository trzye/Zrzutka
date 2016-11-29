package trzye.zrzutka.fatclient.friendsfragment

import trzye.zrzutka.androidmvp.PresentersWaitingRoom
import trzye.zrzutka.model.ModelProvider

object FriendsFragmentWaitingRoom : PresentersWaitingRoom<FriendsFragmentContract.Presenter>(){
    override fun initPresenter(): FriendsFragmentContract.Presenter {
        return FriendsFragmentPresenter(ModelProvider.databaseService)
    }
}