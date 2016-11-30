package trzye.zrzutka.fatclient.contributorsfragment

import trzye.zrzutka.fatclient.choosecontributorsdialog.ChooseContributorsDialogContract
import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.fatclient.readfrienddialog.ReadFriendDialogContract
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
        fun hideContributorRemovedInfoWithUndoOption()
        fun getReadFriendDialog() : ReadFriendDialogContract.View
        fun getChooseContributorsDialog(): ChooseContributorsDialogContract.View
    }

    abstract class Presenter : IContributionContract.IContributionPresenter<View>() {
        abstract fun addNewContributor()
        abstract fun removeContributor(position: Int)
        abstract fun undoLastContributorRemove()
        abstract fun showFriendData(position: Int)
        abstract fun bindData()
        abstract fun startDialogIfExists()

    }
}

