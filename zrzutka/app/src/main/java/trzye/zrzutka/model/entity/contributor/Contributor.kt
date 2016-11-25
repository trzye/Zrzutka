package trzye.zrzutka.model.entity.contributor

import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.doCopy
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.friend.Friend
import javax.persistence.*

@Entity
class Contributor private constructor (
        @Id @GeneratedValue val id: Long? = null,
        @JoinColumn val friend: Friend,
        @ManyToOne private val _charges: MutableCollection<Charge>,
        @OneToOne val contribution: Contribution
) : Copyable<Contributor>{

    private constructor() : this(Friend(""), Contribution(""))
    constructor(friend: Friend, contribution: Contribution) : this(null, friend, mutableListOf(), contribution)

    val charges: List<Charge>
        get() = _charges.toList()

    override fun doCopy(): Contributor {
        return Contributor(id, friend.doCopy(), _charges.doCopy(), contribution)
    }
}

