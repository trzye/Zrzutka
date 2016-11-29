package trzye.zrzutka.model.entity.purchase

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toMoneyDouble
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.getColor
import trzye.zrzutka.model.entity.randColor
import javax.persistence.*

@Entity
class Purchase private constructor(
        @Id @GeneratedValue val id: Long? = null,
        name: String,
        price: Long,
        @OneToOne var contribution: Contribution?,
        @Column var colorId: Int = randColor(),
        @ManyToOne val _charges: MutableCollection<Charge> = mutableListOf()
) : BaseObservable(), Copyable<Purchase> {

    private constructor() : this("", 0)
    constructor(name: String, price: Long) : this(null, name, price, null)

    @Column var name: String = name
        set(value) {field = value; notifyChange()}

    @Column var price: Long = price
        set(value) {field = value; notifyChange()}

    val charges: List<Charge>
        get() = _charges.toList()

    fun getReadablePrice() = price.toMoneyDouble().toReadablePriceString()

    fun getColor() = getColor(colorId)

    override fun doCopy(): Purchase {
        val copy =  Purchase(id, name, price, null, colorId)
        return copy
    }

}

