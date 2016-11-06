package pl.edu.pw.jereczem.zrzutka.client.model.contribution

import pl.edu.pw.jereczem.zrzutka.client.model.friend.Friend
import javax.persistence.*

@Entity
data class Contributor private constructor (
        @Id @GeneratedValue val id: Long? = null,
        @JoinColumn val friend: Friend = Friend(),
        @OneToOne var contribution: Contribution? = null
){
    private constructor() : this(null)
    constructor(friend: Friend, contribution: Contribution) : this(null, friend, contribution)
    constructor(friend: Friend) : this(null, friend, null)

    @ManyToOne private val _charges: MutableCollection<Charge> = mutableListOf()
    val charges: List<Charge> get() = _charges.toList()
}

