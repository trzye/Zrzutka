package trzye.zrzutka.model.entity.purchase

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toMoneyDouble
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.getColor
import trzye.zrzutka.model.entity.randColor

class Purchase private constructor(
        name: String,
        price: Long,
        var contribution: Contribution?,
        var colorId: Int = randColor(),
        private var jooqPurchase: trzye.zrzutka.jooq.model.tables.pojos.Purchase? = null,
        val _charges: MutableCollection<Charge> = mutableListOf()
    ) : BaseObservable(), Copyable<Purchase> {

    constructor(name: String, price: Long) : this(name, price, null)

    var name = name
        set(value) {field = value; notifyChange()}

    var price = price
        set(value) {field = value; notifyChange()}

    val charges: List<Charge>
        get() = _charges.toList()

    fun getReadablePrice() = price.toMoneyDouble().toReadablePriceString()

    fun getColor() = getColor(colorId)

    override fun doCopy(): Purchase {
        val copy =  Purchase(name, price, null, colorId, jooqPurchase)
        return copy
    }

    constructor(
            jooqPurchase: trzye.zrzutka.jooq.model.tables.pojos.Purchase,
            contribution: Contribution?,
            charges: MutableList<Charge>
    ) : this(
            jooqPurchase.name,
            jooqPurchase.price,
            contribution,
            jooqPurchase.colorid,
            jooqPurchase = jooqPurchase,
            _charges = charges
    )

    fun databasePojo(): trzye.zrzutka.jooq.model.tables.pojos.Purchase {
        return trzye.zrzutka.jooq.model.tables.pojos.Purchase(
                colorId,
                contribution?.databasePojo()?.id?.toLong(),
                jooqPurchase?.id,
                name,
                price
        ).also { jooqPurchase = it }
    }

    fun setId(id: Int): trzye.zrzutka.jooq.model.tables.pojos.Purchase {
        jooqPurchase?.id =id
        return databasePojo()
    }

}

