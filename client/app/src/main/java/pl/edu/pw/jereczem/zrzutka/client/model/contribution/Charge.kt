package pl.edu.pw.jereczem.zrzutka.client.model.contribution

import javax.persistence.*

@Entity
data class Charge private constructor(
        @Id @GeneratedValue val id: Long? = null,
        @OneToOne var charged: Contributor? = null,
        @OneToOne var purchase: Purchase? = null,
        @Column var amountToPay: Double = 0.0,
        @Column val amountPaid: Double = 0.0
){
    private constructor() : this(null)
    constructor(charged: Contributor, purchase: Purchase, amountToPay: Double = 0.0, amountPaid: Double = 0.0)
        : this(null, charged, purchase, amountToPay, amountPaid)
}