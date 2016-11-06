package pl.edu.pw.jereczem.zrzutka.client.model.contribution

import pl.edu.pw.jereczem.zrzutka.client.controller.randColor
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

    fun addCharge(charge: Charge){
        _charges.add(charge)
    }

    fun removeCharge(chargeToRemove: Charge) {
        _charges.remove(chargeToRemove)
        chargeToRemove.charged = null
        chargeToRemove.purchase = null
        if(_charges.isNotEmpty()){
            _charges.forEach { c -> c.amountToPay += chargeToRemove.amountToPay/_charges.size }
        }
    }

}