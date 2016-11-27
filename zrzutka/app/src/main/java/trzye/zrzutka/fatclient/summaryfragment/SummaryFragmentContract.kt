package trzye.zrzutka.fatclient.summaryfragment

import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.model.entity.contribution.Contribution

interface SummaryFragmentContract : IContributionContract {

    interface View : IContributionContract.IContributionView<Presenter> {
        fun bindData(contribution: Contribution)
        fun changeDataSet(contribution: Contribution)
    }

    abstract class Presenter : IContributionContract.IContributionPresenter<View>() {
        abstract fun bindData()
    }
}

