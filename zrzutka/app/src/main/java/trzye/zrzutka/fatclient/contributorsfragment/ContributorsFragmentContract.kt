package trzye.zrzutka.fatclient.contributorsfragment

import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.model.entity.contribution.Contribution

interface ContributorsFragmentContract : IContributionContract {

    interface View : IContributionContract.IContributionView<Presenter>{
        fun bindData(contribution: Contribution)
        fun changeDataSet(contribution: Contribution)
        fun notifyContributorAdded(position: Int, listSize: Int)
        fun notifyContributorRemoved(position: Int, listSize: Int)
        fun hideDeleteIcons()
        fun showDeleteIcons()
        fun hideAddButton()
        fun showAddButton()
        fun showContributorRemovedInfoWithUndoOption()
    }

    interface Presenter : IContributionContract.IContributionPresenter<View> {
        fun addNewContributor()
        fun removeContributor(position: Int)
        fun undoLastContributorRemove()
        fun showFriendData(position: Int)
    }
}

