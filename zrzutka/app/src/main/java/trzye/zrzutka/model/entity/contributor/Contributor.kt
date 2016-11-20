package trzye.zrzutka.model.entity.contributor

import trzye.zrzutka.common.extensions.Cloneable
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.friend.Friend
import javax.persistence.*

@Entity
data class Contributor private constructor (
        @Id @GeneratedValue val id: Long? = null,
        @JoinColumn val friend: Friend = Friend(),
        @OneToOne var contribution: Contribution? = null
) : Cloneable<Contributor>{
    private constructor() : this(null)
    constructor(friend: Friend, contribution: Contribution) : this(null, friend, contribution)
    constructor(friend: Friend) : this(null, friend, null)

    @ManyToOne private val _charges: MutableCollection<Charge> = mutableListOf()
    val charges: List<Charge> get() = _charges.toList()

    override fun clone(): Contributor {
        return copy()
    }
}

