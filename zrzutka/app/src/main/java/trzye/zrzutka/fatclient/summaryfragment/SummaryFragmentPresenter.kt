package trzye.zrzutka.fatclient.summaryfragment

import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.summaryfragment.SummaryFragmentContract.View
import trzye.zrzutka.model.entity.summary.SortedColumn

class SummaryFragmentPresenter() : SummaryFragmentContract.Presenter() {

    private lateinit var dataHolder: ContributionDataHolder

    override fun init(dataHolder: ContributionDataHolder) {
        this.dataHolder = dataHolder
    }

    override fun bindData(){
        view.bindData(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }

    override fun setEditMode() {
        dataHolder.isEditable = true
        view.setPreciseModeSwitchActive()
    }

    override fun setReadMode() {
        dataHolder.isEditable = false
        view.setPreciseModeSwitchInactive()
    }

    override fun updateView(view: View) {
        this.view = view
        view.changeDataSet(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }

    override fun changePreciseMode() {
        val summary = dataHolder.contribution.summary
        summary.preciseCalculation = !summary.preciseCalculation
        view.changeDataSet(dataHolder.contribution)
    }

    override fun generateSummaryUrl() {
        //TODO
    }

    override fun orderByWhoPaysHeader() {
        orderBy(SortedColumn.WHO_PAYS)
    }

    override fun orderByToWhomHeader() {
        orderBy(SortedColumn.TO_WHOM)
    }

    override fun orderByAmountHeader() {
        orderBy(SortedColumn.AMOUNT)
    }

    private fun orderBy(sortedSummary: SortedColumn) {
        if (dataHolder.contribution.summary.sortedColumn == sortedSummary) {
            dataHolder.contribution.summary.isSortedDescending = !dataHolder.contribution.summary.isSortedDescending
        } else {
            dataHolder.contribution.summary.sortedColumn = sortedSummary
        }
        view.changeDataSet(dataHolder.contribution)
    }

}

