package trzye.zrzutka.model.entity.summary

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toMoneyDouble
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.getColor
import trzye.zrzutka.model.entity.randColor
import javax.persistence.*

@Entity
class Summary private constructor(
        @Id @GeneratedValue val id: Long?,
        preciseCalculation: Boolean = false,
        @Column var isSortedDescending: Boolean,
        @Column var sortedColumn: SortedColumn,
        @Column var colorSummaryId: Int = randColor(),
        @Column var colorPriceSumId: Int = randColor(),
        priceSum: Long = 0,
        jooqSummary: trzye.zrzutka.jooq.model.tables.pojos.Summary? = null
) : Copyable<Summary>, BaseObservable(){

    @Column var preciseCalculation: Boolean = preciseCalculation
        set(value) {field = value; notifyChange()}

    @Column var priceSum: Long = priceSum
        set(value) {field = value; notifyChange()}

    fun getReadablePriceSum() = priceSum.toMoneyDouble().toReadablePriceString()

    val jooqSummary = jooqSummary ?: trzye.zrzutka.jooq.model.tables.pojos.Summary(
            colorPriceSumId,
            colorSummaryId,
            id?.toInt(),
            if(isSortedDescending) 1 else 0,
            if(preciseCalculation) 1 else 0,
            priceSum,
            sortedColumn.name
    )

    constructor(jooqSummary: trzye.zrzutka.jooq.model.tables.pojos.Summary) : this(
            jooqSummary.id?.toLong(),
            jooqSummary.precisecalculation > 0,
            jooqSummary.issorteddescending > 0,
            SortedColumn.valueOf(jooqSummary.sortedcolumn),
            jooqSummary.colorsummaryid,
            jooqSummary.colorpricesumid,
            jooqSummary = jooqSummary
    )

    private constructor() : this(false, false, SortedColumn.WHO_PAYS)

    fun getSummaryColor() = getColor(colorSummaryId)

    fun getPriceSumColor() = getColor(colorPriceSumId)

    constructor(preciseCalculation: Boolean, isSortedDescending: Boolean, sortedColumn: SortedColumn) : this(null, preciseCalculation, isSortedDescending, sortedColumn)

    override fun doCopy(): Summary {
       return Summary(id, preciseCalculation, isSortedDescending, sortedColumn, colorSummaryId, colorPriceSumId, priceSum, jooqSummary)
    }

    fun setBy(summary: Summary) {
        preciseCalculation = summary.preciseCalculation
        isSortedDescending = summary.isSortedDescending
        sortedColumn = summary.sortedColumn
    }
}

