package trzye.zrzutka.model

import trzye.zrzutka.model.entity.contribution.Contribution

interface IDatabaseService {
    fun getContribution(contributionId: Long?): Contribution
    fun save(contribution: Contribution): Long
}