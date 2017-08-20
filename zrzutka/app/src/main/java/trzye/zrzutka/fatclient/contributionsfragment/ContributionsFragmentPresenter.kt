package trzye.zrzutka.fatclient.contributionsfragment

import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.model.ContributionsMerger
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.dto.ContributionsDTO
import trzye.zrzutka.model.entity.contribution.Contribution

class ContributionsFragmentPresenter(private val databaseService: IDatabaseService) : ContributionsFragmentContract.Presenter() {

    private var newContributionDialogPresenter: ContributionDialogContract.Presenter? = null
    var contributionsDTO: ContributionsDTO = ContributionsDTO(databaseService.getAllContributions().toMutableList())
    lateinit var lastState: ContributionsDTO

    override fun createNewContribution() {
        newContributionDialogPresenter = view.getContributionDialogView().apply {
            startAsCreateNewContributionDialog()
        }.presenter
    }

    override fun startDialogIfExists() {
        val presenter = newContributionDialogPresenter
        if ((presenter != null) && !presenter.isDone()) {
            view.getContributionDialogView().start(presenter)
        }
    }

    override fun bindData() {
        val checked = contributionsDTO.checked
        contributionsDTO = ContributionsDTO(databaseService.getAllContributions().toMutableList())
        checked.forEachIndexed { i, b -> if(i < contributionsDTO.checked.size) contributionsDTO.checked[i]=b}
        view.bindData(contributionsDTO)
    }

    override fun openContributionInEditMode(position: Int) {
        val contribution = contributionsDTO.contributions.getOrNull(position)
        if(contribution?.databasePojo()?.id != null)
            view.getContributionActivityView().startAsEditableContributionActivity(contribution.databasePojo().id.toLong())
    }

    override fun openContributionInReadMode(position: Int){
        val contribution = contributionsDTO.contributions.getOrNull(position)
        if(contribution?.databasePojo()?.id != null)
            view.getContributionActivityView().startAsReadOnlyContributionActivity(contribution.databasePojo().id.toLong())
    }

    override fun changeCheckedOption(position: Int) {
        if(position < contributionsDTO.checked.size){
            contributionsDTO.checked[position] = contributionsDTO.checked[position].not()
            view.notifyContributionChanged(position)
        }
        setToolbar()
    }

    override fun removeCheckedContributions() {
        var indexOfFirstChecked = contributionsDTO.checked.indexOfFirst { it }
        lastState = contributionsDTO.doCopy()
        while(indexOfFirstChecked != -1){
            databaseService.removeContribution(contributionsDTO.contributions[indexOfFirstChecked].databasePojo().id.toLong())
            contributionsDTO.contributions.removeAt(indexOfFirstChecked)
            contributionsDTO.checked.removeAt(indexOfFirstChecked)
            indexOfFirstChecked = contributionsDTO.checked.indexOfFirst { it == true }
        }
        val deleted = lastState.contributions.size - contributionsDTO.contributions.size
        if(deleted > 0){
            view.dataSetChanged(contributionsDTO)
            when(deleted) {
                1 -> view.showOneContributionRemovedInfo()
                else -> view.showContributionsRemovedInfo()
            }
        }
    }

    override fun undoRemovingContributions() {
        contributionsDTO = lastState
        lastState.contributions.forEach { databaseService.save(it) }
        view.dataSetChanged(contributionsDTO)
    }

    override fun mergeCheckedContributions() {
        view.hideInformationAboutRemovingContribution()
        if(contributionsDTO.checked.count { it == true } > 1) {
            val contributionsToMerge = mutableListOf<Contribution>()
            contributionsDTO.checked.forEachIndexed { i, isChecked ->
                if(isChecked) contributionsToMerge.add(contributionsDTO.contributions[i])
            }
            var newContribution: Contribution = ContributionsMerger.merge(contributionsToMerge)
            val id = databaseService.save(newContribution)
            newContribution = databaseService.getContribution(id)
            contributionsDTO.contributions.add(0, newContribution)
            contributionsDTO.checked.add(0, false)
            view.notifyContributionAdded(0, contributionsDTO.contributions.size)
        } else {
            view.showMergeAtLeastTwoContributions()
        }
    }

    override fun setToolbar() {
        if (contributionsDTO.checked.filter { it }.isNotEmpty()) {
            view.setToolbarForEdition()
        } else {
            view.setToolbarForStandardMode()
        }
    }

}

