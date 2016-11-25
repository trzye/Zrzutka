package trzye.zrzutka.model.entity.purchase

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.doCopy
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.randColor
import javax.persistence.*

@Entity
class Purchase private constructor(
        @Id @GeneratedValue val id: Long? = null,
        name: String,
        price: Double,
        @OneToOne val contribution: Contribution,
        @Column val colorId: Int = randColor(),
        @ManyToOne val _charges: MutableCollection<Charge> = mutableListOf()
) : BaseObservable(), Copyable<Purchase> {

    private constructor() : this("", 0.0, Contribution(""))
    constructor(name: String, price: Double, contribution: Contribution) : this(null, name, price, contribution)

    @Column var name: String = name
        set(value) {field = value; notifyChange()}

    @Column var price: Double = price
        set(value) {field = value; notifyChange()}

    val charges: List<Charge>
        get() = _charges.toList()

    fun getReadablePrice() = price.toReadablePriceString()

    override fun doCopy(): Purchase {
        return Purchase(id, name, price, contribution, colorId, _charges.doCopy())
    }

}