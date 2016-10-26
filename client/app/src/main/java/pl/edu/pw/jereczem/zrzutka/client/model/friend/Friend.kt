package pl.edu.pw.jereczem.zrzutka.client.model.friend

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Friend private constructor(
        @Id @GeneratedValue val id: Long? = null,
        @Column var nickname: String = "",
        @Column var firstName: String = "",
        @Column var lastName: String = ""
        //        val paymentDetails: List<PaymentInformation> = emptyList(),
        //        val contactDetails: List<ContactInformation> = emptyList()
){
    private constructor() : this(null)
    constructor(nickname: String = "", firstName: String = "", lastName: String = "") : this(null, nickname, firstName, lastName)
}

