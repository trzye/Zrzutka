package trzye.zrzutka.fatclient.contributionfragment

import trzye.zrzutka.model.entity.contribution.Contribution

class ContributionDataHolder(var contribution: Contribution, var isEditable: Boolean = false, val views: MutableList<IContributionContract.IContributionView<*>> = mutableListOf()) {
    var isStartedAsRead: Boolean = false
}