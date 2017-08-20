package trzye.zrzutka.model.entity.contributor

import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.friend.Friend
import javax.persistence.*

@Entity
class Contributor private constructor (
        @Id @GeneratedValue val id: Long? = null,
        @OneToOne val friend: Friend,
        @OneToOne var contribution: Contribution?,
        jooqContributor: trzye.zrzutka.jooq.model.tables.pojos.Contributor? = null,
        @ManyToOne val _charges: MutableCollection<Charge> = mutableListOf()
) : Copyable<Contributor>{

    private constructor() : this(Friend(""))
    constructor(friend: Friend) : this(null, friend, null)

    val charges: List<Charge>
        get() = _charges.toList()

    override fun doCopy(): Contributor {
        val copy = Contributor(id, friend.doCopy(), contribution, jooqContributor)
        return copy
    }

    val jooqContributor = jooqContributor ?: trzye.zrzutka.jooq.model.tables.pojos.Contributor(
            contribution?.id,
            friend.id,
            id?.toInt()
    )

    //TODO
    constructor(
            jooqContributor: trzye.zrzutka.jooq.model.tables.pojos.Contributor,
            jooqFriend: Friend,
            jooqContribution: Contribution?,
            jooqCharges: MutableList<Charge>
    ) : this(
            jooqContributor.id?.toLong(),
            jooqFriend,
            jooqContribution,
            jooqContributor,
            jooqCharges
    )
}

