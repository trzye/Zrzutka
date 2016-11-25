package trzye.zrzutka.model.entity.contribution

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.doCopy
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.purchase.Purchase
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
class Contribution private constructor(
        @Id @GeneratedValue val id: Long?,
        title: String,
        startDate: Date,
        endDate: Date,
        @ManyToOne val _contributors: MutableCollection<Contributor> = mutableListOf(),
        @ManyToOne val _purchases: MutableCollection<Purchase> = mutableListOf()
) : BaseObservable(), Copyable<Contribution> {

    private constructor() : this("")
    constructor(title: String, startDate: Date = Date(), endDate: Date = startDate) : this(null, title, startDate, endDate)

    @Column var title: String = title
        set(value) {field = value; notifyChange()}

    @Column(columnDefinition = "DATE") var startDate: Date = startDate
        set(value) {field = value; notifyChange()}

    @Column(columnDefinition = "DATE") var endDate: Date = endDate
        set(value) {field = value; notifyChange()}

    val contributors: List<Contributor>
        get() = _contributors.toList()

    val purchases: List<Purchase>
        get() = _purchases.toList()

    fun getReadableEndDate() : String = SimpleDateFormat("dd.MM.yyyy", Locale.US).format(endDate)

    fun getReadableStartDate() : String = SimpleDateFormat("dd.MM.yyyy", Locale.US).format(startDate)

    fun getReadableDateRanges() : String {
        if(getReadableEndDate() == getReadableStartDate()){
            return getReadableStartDate()
        } else {
            return "${getReadableStartDate()} - ${getReadableEndDate()}"
        }
    }

    override fun doCopy(): Contribution {
        val clone = Contribution(id, title, startDate.doCopy(), endDate.doCopy(), _contributors.doCopy(), _purchases.doCopy())
        return clone
    }

}


