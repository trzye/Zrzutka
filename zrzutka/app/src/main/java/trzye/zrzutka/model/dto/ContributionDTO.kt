package trzye.zrzutka.model.dto

import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.doCopy
import trzye.zrzutka.model.entity.contribution.Contribution

class ContributionsDTO(val contributions: MutableList<Contribution>) : Copyable<ContributionsDTO>{

    override fun doCopy(): ContributionsDTO {
        return ContributionsDTO(contributions.doCopy())
    }

    val checked: MutableList<Boolean> = contributions.flatMap { listOf(false) }.toMutableList()
}