package trzye.zrzutka.presenter

import trzye.zrzutka.model.entity.Contribution

interface IContributionDialogPresenter {
    fun createNewContribution()
    fun editBaseContributionData(contribution: Contribution)
}
