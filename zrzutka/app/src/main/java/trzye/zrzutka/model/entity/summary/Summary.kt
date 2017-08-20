package trzye.zrzutka.model.entity.summary

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toMoneyDouble
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.getColor
import trzye.zrzutka.model.entity.randColor

class Summary private constructor(
        preciseCalculation: Boolean = false,
        var isSortedDescending: Boolean,
        var sortedColumn: SortedColumn,
        var colorSummaryId: Int = randColor(),
        var colorPriceSumId: Int = randColor(),
        priceSum: Long = 0,
        private var jooqSummary: trzye.zrzutka.jooq.model.tables.pojos.Summary? = null
) : Copyable<Summary>, BaseObservable(){

    var preciseCalculation = preciseCalculation
        set(value) {field = value; notifyChange()}

    var priceSum = priceSum
        set(value) {field = value; notifyChange()}

    fun getReadablePriceSum() = priceSum.toMoneyDouble().toReadablePriceString()

    fun getSummaryColor() = getColor(colorSummaryId)

    fun getPriceSumColor() = getColor(colorPriceSumId)

    constructor(preciseCalculation: Boolean, isSortedDescending: Boolean, sortedColumn: SortedColumn) :
            this(preciseCalculation, isSortedDescending, sortedColumn, jooqSummary = null)

    override fun doCopy(): Summary {
       return Summary(preciseCalculation, isSortedDescending, sortedColumn, colorSummaryId, colorPriceSumId, priceSum, jooqSummary)
    }

    fun setBy(summary: Summary) {
        preciseCalculation = summary.preciseCalculation
        isSortedDescending = summary.isSortedDescending
        sortedColumn = summary.sortedColumn
    }

    constructor(jooqSummary: trzye.zrzutka.jooq.model.tables.pojos.Summary) : this(
            jooqSummary.precisecalculation > 0,
            jooqSummary.issorteddescending > 0,
            SortedColumn.valueOf(jooqSummary.sortedcolumn),
            jooqSummary.colorsummaryid,
            jooqSummary.colorpricesumid,
            jooqSummary = jooqSummary
    )

    fun databasePojo(): trzye.zrzutka.jooq.model.tables.pojos.Summary {
        return trzye.zrzutka.jooq.model.tables.pojos.Summary(
                colorPriceSumId,
                colorSummaryId,
                jooqSummary?.id,
                if (isSortedDescending) 1 else 0,
                if (preciseCalculation) 1 else 0,
                priceSum,
                sortedColumn.name
        ).also { jooqSummary = it }
    }

    fun setId(id: Int) {
        jooqSummary?.id =id
    }
}

