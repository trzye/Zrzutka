package trzye.zrzutka.model.entity.contributor

import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.friend.Friend

class Contributor private constructor (
        val friend: Friend,
        var contribution: Contribution?,
        private var jooqContributor: trzye.zrzutka.jooq.model.tables.pojos.Contributor? = null,
        val _charges: MutableCollection<Charge> = mutableListOf()
) : Copyable<Contributor>{

    private constructor() : this(Friend(""))
    constructor(friend: Friend) : this(friend, null)

    val charges: List<Charge>
        get() = _charges.toList()

    override fun doCopy(): Contributor {
        val copy = Contributor(friend.doCopy(), contribution, jooqContributor)
        return copy
    }

    constructor(
            jooqContributor: trzye.zrzutka.jooq.model.tables.pojos.Contributor,
            jooqFriend: Friend,
            jooqContribution: Contribution?,
            jooqCharges: MutableList<Charge>
    ) : this(
            jooqFriend,
            jooqContribution,
            jooqContributor,
            jooqCharges
    )

    fun databasePojo() = trzye.zrzutka.jooq.model.tables.pojos.Contributor(
            contribution?.databasePojo()?.id?.toLong(),
            friend.databasePojo().id.toLong(),
            jooqContributor?.id
    ).also { jooqContributor = it }

    fun setId(id: Int): trzye.zrzutka.jooq.model.tables.pojos.Contributor {
        jooqContributor?.id =id
        return databasePojo()
    }

}

