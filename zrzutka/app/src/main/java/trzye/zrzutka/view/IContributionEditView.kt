package trzye.zrzutka.view

import trzye.zrzutka.model.entity.Contribution

interface IContributionEditView : IView<Contribution> {
    fun  getContributionId(): Long?
    fun showEmptyTitleError()
}