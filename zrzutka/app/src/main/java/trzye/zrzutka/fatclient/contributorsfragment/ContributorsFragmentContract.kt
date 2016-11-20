package trzye.zrzutka.fatclient.contributorsfragment

import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.mvp.IContract

interface ContributorsFragmentContract : IContract {

    interface View : IContract.IView<Presenter>{
        fun bindData(contribution: Contribution)
        fun notifyContributorAdded(listSize: Int)
        fun notifyContributorRemoved(position: Int, listSize: Int)
        fun hideDeleteIcons()
        fun showDeleteIcons()
        fun hideAddButton()
        fun showAddButton()
        fun showContributorRemovedInfoWithUndoOption()
    }

    interface Presenter : IContract.IPresenter<View> {
        fun addNewContributor()
        fun removeContributor(position: Int)
        fun undoLastContributorRemove()
        fun showFriendData(position: Int)
        fun setEditMode()
        fun setReadMode()
        fun init(contribution: Contribution)
    }
}

