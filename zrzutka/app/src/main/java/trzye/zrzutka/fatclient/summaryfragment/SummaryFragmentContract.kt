package trzye.zrzutka.fatclient.summaryfragment

import trzye.zrzutka.fatclient.contributionfragment.IContributionContract
import trzye.zrzutka.model.entity.contribution.Contribution

interface SummaryFragmentContract : IContributionContract {

    interface View : IContributionContract.IContributionView<Presenter> {
        fun bindData(contribution: Contribution)
        fun changeDataSet(contribution: Contribution)
        fun setPreciseModeSwitchActive()
        fun setPreciseModeSwitchInactive()
    }

    abstract class Presenter : IContributionContract.IContributionPresenter<View>() {
        abstract fun bindData()
        abstract fun changePreciseMode()
        abstract fun generateSummaryUrl()
        abstract fun orderByWhoPaysHeader()
        abstract fun orderByToWhomHeader()
        abstract fun orderByAmountHeader()
    }
}

