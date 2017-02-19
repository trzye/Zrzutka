package trzye.zrzutka.model.entity.summary

import android.databinding.BaseObservable
import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.common.extensions.toMoneyDouble
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.contribution.Contribution
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
        priceSum: Long = 0
) : Copyable<Summary>, BaseObservable(){

    @Column var preciseCalculation: Boolean = preciseCalculation
        set(value) {field = value; notifyChange()}

    @Column var priceSum: Long = priceSum
        set(value) {field = value; notifyChange()}

    fun getReadablePriceSum() = priceSum.toMoneyDouble().toReadablePriceString()

    private constructor() : this(false, false, SortedColumn.WHO_PAYS)

    fun getSummaryColor() = getColor(colorSummaryId)

    fun getPriceSumColor() = getColor(colorPriceSumId)

    constructor(preciseCalculation: Boolean, isSortedDescending: Boolean, sortedColumn: SortedColumn) : this(null, preciseCalculation, isSortedDescending, sortedColumn)

    override fun doCopy(): Summary {
       return Summary(id, preciseCalculation, isSortedDescending, sortedColumn, colorSummaryId, colorPriceSumId)
    }

    fun setBy(summary: Summary) {
        preciseCalculation = summary.preciseCalculation
        isSortedDescending = summary.isSortedDescending
        sortedColumn = summary.sortedColumn
    }
}

