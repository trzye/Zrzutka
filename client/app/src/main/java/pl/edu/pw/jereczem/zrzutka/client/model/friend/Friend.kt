package pl.edu.pw.jereczem.zrzutka.client.model.friend

import pl.edu.pw.jereczem.zrzutka.client.view.common.randColor
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Friend private constructor(
        @Id @GeneratedValue val id: Long? = null,
        @Column var nickname: String = "",
        @Column var firstName: String = "",
        @Column var lastName: String = "",
        @Column val colorId: Int = randColor()

        //        val paymentDetails: List<PaymentInformation> = emptyList(),
        //        val contactDetails: List<ContactInformation> = emptyList()
){
    private constructor() : this(null)
    constructor(nickname: String = "", firstName: String = "", lastName: String = "") : this(null, nickname, firstName, lastName)

    fun getShowingName(): String {
        if(nickname.isNotEmpty())
            return nickname
        if(lastName.isNotEmpty())
            return firstName + " " + lastName
        return firstName
    }

}

