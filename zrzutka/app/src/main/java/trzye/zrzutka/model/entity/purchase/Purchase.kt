package pl.edu.pw.jereczem.zrzutka.client.model.contribution

import trzye.zrzutka.model.entity.randColor
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import javax.persistence.*

@Entity
data class Purchase private constructor(
        @Id @GeneratedValue val id: Long? = null,
        @Column var name: String = "",
        @Column var price: Double = 0.0,
        @OneToOne var contribution: Contribution? = null,
        @Column val colorId: Int = randColor()
        ){

    private constructor() : this(null)
    constructor(name: String, price: Double = 0.0, contribution: Contribution) : this(null, name, price, contribution)
    constructor(name: String, price: Double = 0.0) : this(null, name, price, null)

    @ManyToOne private val _charges: MutableCollection<Charge> = mutableListOf()
    val charges: List<Charge> get() = _charges.toList()

}