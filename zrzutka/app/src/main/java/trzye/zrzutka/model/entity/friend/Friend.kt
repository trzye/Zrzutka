package trzye.zrzutka.model.entity.friend

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Cloneable
import trzye.zrzutka.model.entity.getColor
import trzye.zrzutka.model.entity.randColor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Friend private constructor(@Id @GeneratedValue val id: Long? = null, nickname: String = "", firstName: String = "", lastName: String = "") : BaseObservable(), Cloneable<Friend>{

    constructor() : this(null)
    constructor(nickname: String = "", firstName: String = "", lastName: String = "") : this(null, nickname, firstName, lastName)

    @Column var nickname: String = nickname
        set(value) {field = value; notifyChange()}

    @Column var firstName: String = firstName
        set(value) {field = value; notifyChange()}
    @Column var lastName: String = lastName
        set(value) {field = value; notifyChange()}

    @Column val colorId: Int = randColor()

    fun getShowingName(): String {
        if(nickname.isNotEmpty())
            return nickname
        if(lastName.isNotEmpty())
            return firstName + " " + lastName
        return firstName
    }

    fun getInitials(): String {
        val split = getShowingName().split(" ")
        var initials = split.first().toUpperCase().getOrElse(0, { ' ' }).toString()
        if (split.size > 1)
            initials += split[1].toUpperCase().first()
        return initials
    }

    fun getColor() = getColor(colorId)

    override fun clone(): Friend {
        return Friend(id, nickname, firstName, lastName)
    }

}

