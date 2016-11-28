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
        nickname: String,
        firstName: String,
        lastName: String,
        @Column var colorId: Int = randColor()
//        @ManyToOne val _contributors: MutableCollection<Contributor> = mutableListOf()
) : BaseObservable(), Copyable<Friend>{

    private constructor() : this("")
    constructor(nickname: String = "", firstName: String = "", lastName: String = "") : this(null, nickname, firstName, lastName)

    @Column var nickname: String = nickname
        set(value) {field = value; notifyChange()}

    @Column var firstName: String = firstName
        set(value) {field = value; notifyChange()}

    @Column var lastName: String = lastName
        set(value) {field = value; notifyChange()}


    fun getShowingName(): String {
        if(nickname.isNotEmpty())
            return nickname
        if(lastName.isNotEmpty())
            return firstName + " " + lastName
        return firstName
    }

    fun getInitials(): String = getShowingName().createShort()

    fun getColor() = getColor(colorId)

    override fun doCopy(): Friend {
        return Friend(id, nickname, firstName, lastName, colorId)
    }

    fun setBy(friend: Friend) {
        nickname = friend.nickname
        firstName = friend.firstName
        lastName = friend.lastName
        colorId = friend.colorId
    }

}

