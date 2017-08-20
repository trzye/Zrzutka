package trzye.zrzutka.model.entity.contribution

import android.databinding.BaseObservable
import trzye.zrzutka.common.UniqueNanoTimeGenerator
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

class Contribution private constructor(
        title: String,
        startDate: Date,
        endDate: Date,
        val colorId: Int = randColor(),
        val summary: Summary = Summary(false, false, WHO_PAYS),
        private var jooqContribution: trzye.zrzutka.jooq.model.tables.pojos.Contribution? = null,
        val _contributors: MutableCollection<Contributor> = mutableListOf(),
        val _purchases: MutableCollection<Purchase> = mutableListOf()
) : BaseObservable(), Copyable<Contribution> {

    private constructor() : this("")
    constructor(title: String, startDate: Date = Date(), endDate: Date = startDate) : this(title, startDate, endDate, jooqContribution = null)

    var title: String = title
        set(value) {field = value; notifyChange()}

    var startDate: Date = startDate
        set(value) {field = value; notifyChange()}

    var endDate: Date = endDate
        set(value) {field = value; notifyChange()}

    val contributors: List<Contributor>
        get() = _contributors.toList()

    val purchases: List<Purchase>
        get() = _purchases.toList()

    val chargeUniqueNumberForSorting: Long  = UniqueNanoTimeGenerator.getUniqueValue()

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
        val clone = Contribution(title, startDate.doCopy(), endDate.doCopy(), colorId, summary.doCopy(), jooqContribution)

        val originToNewContributor : Map<Contributor, Contributor> = contributors.associate {
            Pair(it, it.doCopy().apply { clone._contributors.add(this); contribution = clone })
        }

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

    constructor(
            jooqContribution: trzye.zrzutka.jooq.model.tables.pojos.Contribution,
            jooqSummary: Summary,
            jooqContributors: MutableList<Contributor>,
            jooqPurchases: MutableList<Purchase>
    ) : this(
        jooqContribution.title,
            jooqContribution.startdate,
            jooqContribution.enddate,
            jooqContribution.colorid,
            jooqSummary,
            jooqContribution,
            jooqContributors,
            jooqPurchases
    )

    fun databasePojo() = trzye.zrzutka.jooq.model.tables.pojos.Contribution(
            chargeUniqueNumberForSorting,
            colorId,
            java.sql.Date(endDate.time),
            jooqContribution?.id,
            java.sql.Date(startDate.time),
            summary.databasePojo().id?.toLong(),
            title
    ).also { jooqContribution = it }

    fun setId(id: Int) {
        jooqContribution?.id =id
    }
}


