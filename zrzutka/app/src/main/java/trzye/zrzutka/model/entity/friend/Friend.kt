package trzye.zrzutka.model.entity.friend

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.createShort
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.getColor
import trzye.zrzutka.model.entity.randColor
import javax.persistence.*

@Entity
class Friend private constructor(
        @Id @GeneratedValue val id: Long? = null,
        name: String,
        paymentInformation: String,
        contactInformation: String,
        @Column var colorId: Int = randColor()
) : BaseObservable(), Copyable<Friend>{

    private constructor() : this("")
    constructor(nickname: String, firstName: String = "", lastName: String = "") : this(null, nickname, firstName, lastName)

    @Column var nickname: String = name
        set(value) {field = value; notifyChange()}

    @Column var paymentInformation: String = paymentInformation
        set(value) {field = value; notifyChange()}

    @Column var contactInformation: String = contactInformation
        set(value) {field = value; notifyChange()}


    fun getShowingName(): String = nickname

    fun getInitials(): String = getShowingName().createShort()

    fun getColor() = getColor(colorId)

    override fun doCopy(): Friend {
        return Friend(id, nickname, paymentInformation, contactInformation, colorId)
    }

    fun setBy(friend: Friend) {
        nickname = friend.nickname
        paymentInformation = friend.paymentInformation
        contactInformation = friend.contactInformation
        colorId = friend.colorId
    }

}

