package trzye.zrzutka.fatclient.contributorsfragment

import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentContract.Presenter
import trzye.zrzutka.fatclient.contributorsfragment.ContributorsFragmentContract.View
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contribution.addContributor
import trzye.zrzutka.model.entity.contribution.removeContributor
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.friend.Friend

class ContributorsFragmentPresenter(private val databaseService: IDatabaseService) : Presenter() {

    private var friendNo = 0

    private lateinit var lastState: Contribution
    private lateinit var dataHolder: ContributionDataHolder

    override fun init(dataHolder: ContributionDataHolder) {
        this.dataHolder = dataHolder
    }

    override fun bindData(){
        view.bindData(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }

    override fun addNewContributor() {
        view.hideContributorRemovedInfoWithUndoOption()
        val friend = Friend("TEST ${friendNo++}") //TODO
        val contributor = Contributor(friend)//TODO
//        friend._contributors?.add(contributor)//TODO
        dataHolder.contribution.addContributor(contributor) ///TODO
        view.notifyContributorAdded(dataHolder.contribution.contributors.size-1, dataHolder.contribution.contributors.size)
        databaseService.save(dataHolder.contribution)
    }

    override fun removeContributor(position: Int) {
        if(dataHolder.isEditable){
            lastState = dataHolder.contribution.doCopy()
            val contributor = dataHolder.contribution.contributors.getOrNull(position)
            if(contributor != null){
                dataHolder.contribution.removeContributor(contributor)
                view.notifyContributorRemoved(position, dataHolder.contribution.contributors.size)
                view.showContributorRemovedInfoWithUndoOption()
            }
        }
    }

    override fun undoLastContributorRemove() {
        dataHolder.contribution = lastState
        view.changeDataSet(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }

    override fun showFriendData(position: Int) {
        val contributor = dataHolder.contribution.contributors.getOrNull(position)
        if(contributor != null){
            //TODO z uwzględnieniem isEditable
        }
    }

    override fun setEditMode() {
        dataHolder.isEditable = true
        view.showAddButton()
        view.showDeleteIcons()
    }

    override fun setReadMode() {
        dataHolder.isEditable = false
        view.hideAddButton()
        view.hideDeleteIcons()
    }

    override fun updateView(view: View) {
        this.view = view
        view.changeDataSet(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }
}

