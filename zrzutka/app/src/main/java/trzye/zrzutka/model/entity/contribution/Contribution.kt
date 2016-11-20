package trzye.zrzutka.model.entity.contribution

import android.databinding.BaseObservable
import trzye.zrzutka.model.entity.purchase.Purchase
import trzye.zrzutka.common.extensions.Cloneable
import trzye.zrzutka.common.extensions.clone
import trzye.zrzutka.model.entity.contributor.Contributor
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
class Contribution private constructor(
        @Id @GeneratedValue val id: Long? = null,
        @ManyToOne val _contributors: MutableCollection<Contributor> = mutableListOf(),
        @ManyToOne val _purchases: MutableCollection<Purchase> = mutableListOf()
) : BaseObservable(), Cloneable<Contribution> {

    constructor() : this(null)

    @Column var title: String =""
        set(value) {field = value; notifyChange()}

    @Column(columnDefinition = "DATE") var startDate: Date = Date()
        set(value) {field = value; notifyChange()}

    @Column(columnDefinition = "DATE") var endDate: Date = Date()
        set(value) {field = value; notifyChange()}

    val contributors: MutableList<Contributor>
        get() = _contributors.toMutableList()

    val purchases: MutableList<Purchase>
        get() = _purchases.toMutableList()

    fun getReadableEndDate() : String = SimpleDateFormat("dd.MM.yyyy").format(endDate)

    fun getReadableStartDate() : String = SimpleDateFormat("dd.MM.yyyy").format(startDate)

    fun getReadableDateRanges() : String {
        if(getReadableEndDate() == getReadableStartDate()){
            return getReadableStartDate()
        } else {
            return "${getReadableStartDate()} - ${getReadableEndDate()}"
        }
    }

    override fun clone(): Contribution {
        val clone = Contribution(id, _contributors.clone(), _purchases.clone())
        clone.title = title
        clone.endDate = Date(endDate.time)
        clone.startDate = Date(startDate.time)
        return clone
    }


}


