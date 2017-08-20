package trzye.zrzutka.model.entity.friend

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.createShort
import trzye.zrzutka.model.entity.getColor
import trzye.zrzutka.model.entity.randColor

class Friend private constructor(
        name: String,
        paymentInformation: String,
        contactInformation: String,
        var colorId: Int = randColor(),
        private var jooqFriend: trzye.zrzutka.jooq.model.tables.pojos.Friend? = null
) : BaseObservable(), Copyable<Friend>{

    private constructor() : this("")

    constructor(nickname: String, firstName: String = "", lastName: String = "") : this(nickname, firstName, lastName, jooqFriend = null)

    var nickname = name
        set(value) {field = value; notifyChange();}

    var paymentInformation = paymentInformation
        set(value) {field = value; notifyChange();}

    var contactInformation = contactInformation
        set(value) {field = value; notifyChange();}


    fun getShowingName(): String = nickname

    fun getInitials(): String = getShowingName().createShort()

    fun getColor() = getColor(colorId)

    override fun doCopy(): Friend {
        return Friend(nickname, paymentInformation, contactInformation, colorId, jooqFriend)
    }

    fun setBy(friend: Friend) {
        nickname = friend.nickname
        paymentInformation = friend.paymentInformation
        contactInformation = friend.contactInformation
        colorId = friend.colorId
    }

    constructor(jooqFriend: trzye.zrzutka.jooq.model.tables.pojos.Friend) : this(
            jooqFriend.nickname,
            jooqFriend.paymentinformation,
            jooqFriend.contactinformation,
            jooqFriend.colorid,
            jooqFriend = jooqFriend
    )

    fun databasePojo() = trzye.zrzutka.jooq.model.tables.pojos.Friend(
            colorId,
            contactInformation,
            jooqFriend?.id,
            nickname,
            paymentInformation
    ).also { jooqFriend = it }

    fun setId(id: Int): trzye.zrzutka.jooq.model.tables.pojos.Friend {
        jooqFriend?.id =id
        return databasePojo()
    }
}

