package trzye.zrzutka.model.entity.contribution

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.createShort
import trzye.zrzutka.common.extensions.doCopy
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.purchase.Purchase
import trzye.zrzutka.model.entity.randColor
import trzye.zrzutka.model.entity.summary.SortedColumn.WHO_PAYS
import trzye.zrzutka.model.entity.summary.Summary
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
open class Contribution private constructor(
        @Id @GeneratedValue val id: Long?,
        title: String,
        startDate: Date,
        endDate: Date,
        @OneToOne var summary: Summary,
        @Column val colorId: Int = randColor(),
        @ManyToOne val _contributors: MutableCollection<Contributor> = mutableListOf(),
        @ManyToOne val _purchases: MutableCollection<Purchase> = mutableListOf()
) : BaseObservable(), Copyable<Contribution> {

    protected constructor() : this("")
    constructor(title: String, startDate: Date = Date(), endDate: Date = startDate) : this(null, title, startDate, endDate, Summary(false, false, WHO_PAYS))

    init {
        summary.contribution = this
    }

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

    fun getShortTitle() : String = title.createShort()

    fun getColor() = trzye.zrzutka.model.entity.getColor(colorId)

    fun getReadableDateRanges() : String {
        if(getReadableEndDate() == getReadableStartDate()){
            return getReadableStartDate()
        } else {
            return "${getReadableStartDate()} - ${getReadableEndDate()}"
        }
    }

    override fun doCopy(): Contribution {
        val clone = Contribution(id, title, startDate.doCopy(), endDate.doCopy(), summary.doCopy(), colorId)

        //prawdziwy do kopii
        val originToNewContributor : Map<Contributor, Contributor> = contributors.associate {
            Pair(it, it.doCopy().apply { clone._contributors.add(this); contribution = clone })
        }

        summary.contribution = clone

        purchases.forEach {
            val clonedCharges  = it.charges.doCopy()
            val clonedPurchase = it.doCopy()

            clone._purchases.add(clonedPurchase)
            clonedPurchase.contribution = clone

            clonedCharges.forEach {
                clonedCharge ->
                clonedPurchase._charges.add(clonedCharge)
                clonedCharge.purchase = clonedPurchase

                originToNewContributor[clonedCharge.charged]?._charges?.add(clonedCharge)
                clonedCharge.charged = originToNewContributor[clonedCharge.charged]
            }
        }
        return clone
    }

}


