package trzye.zrzutka.model

import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.friend.Friend

interface IDatabaseService {
    fun getContribution(contributionId: Long?): Contribution
    fun removeContribution(contributionId: Long)
    fun getAllContributions() : List<Contribution>
    fun save(contribution: Contribution): Long
    fun getAllFriends(): List<Friend>
    fun save(friend: Friend)
}