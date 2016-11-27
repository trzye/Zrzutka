package trzye.zrzutka.fatclient.summaryfragment

import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.summaryfragment.SummaryFragmentContract.View

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
    }

    override fun setReadMode() {
        dataHolder.isEditable = false
    }

    override fun updateView(view: View) {
        this.view = view
        view.changeDataSet(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }
}

