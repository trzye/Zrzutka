package trzye.zrzutka.fatclient.contributorsfragment

import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentContract.Presenter
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentContract.View
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contribution.addContributor
import trzye.zrzutka.model.entity.contribution.removeContributor
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.friend.Friend

class ContributorsFragmentPresenter(private var isEditable: Boolean = false) : Presenter {

    private lateinit var contribution: Contribution
    private lateinit var view: View
    private lateinit var lastState: Contribution

    override fun init(contribution: Contribution) {
        this.contribution = contribution
    }

    override fun attachView(view: View) {
        this.view = view
        view.bindData(contribution)
        if(isEditable) setEditMode() else setReadMode()
    }

    override fun addNewContributor() {
        contribution.addContributor(Contributor(Friend("TEST"))) //TODO
        view.notifyContributorAdded(contribution.contributors.size)
    }

    override fun removeContributor(position: Int) {
        if(isEditable){
            lastState = contribution.clone()
            val contributor = contribution.contributors.getOrNull(position)
            if(contributor != null){
                contribution.removeContributor(contributor)
                view.notifyContributorRemoved(position, contribution.contributors.size)
                view.showContributorRemovedInfoWithUndoOption()
            }
        }
    }

    override fun undoLastContributorRemove() {
        contribution = lastState
        view.bindData(contribution)
        if(isEditable) setEditMode() else setReadMode()
    }

    override fun showFriendData(position: Int) {
        val contributor = contribution.contributors.getOrNull(position)
        if(contributor != null){
            //TODO z uwzględnieniem isEditable
        }
    }

    override fun setEditMode() {
        isEditable = true
        view.showAddButton()
        view.showDeleteIcons()
    }

    override fun setReadMode() {
        isEditable = false
        view.hideAddButton()
        view.hideDeleteIcons()
    }
}

