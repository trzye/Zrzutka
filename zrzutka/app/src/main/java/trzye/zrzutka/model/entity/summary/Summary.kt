package trzye.zrzutka.model.entity.summary

import trzye.zrzutka.common.extensions.Copyable
import trzye.zrzutka.model.entity.contribution.Contribution
import javax.persistence.*

@Entity
class Summary private constructor(
        @Id @GeneratedValue val id: Long?,
        @Column var preciseCalculation: Boolean,
        @Column var isSortedDescending: Boolean,
        @Column var sortedColumn: SortedColumn
) : Copyable<Summary>{

    private constructor() : this(false, false, SortedColumn.WHO_PAYS)

    constructor(preciseCalculation: Boolean, isSortedDescending: Boolean, sortedColumn: SortedColumn) : this(null, preciseCalculation, isSortedDescending, sortedColumn)

    override fun doCopy(): Summary {
       return Summary(id, preciseCalculation, isSortedDescending, sortedColumn)
    }

    fun setBy(summary: Summary) {
        preciseCalculation = summary.preciseCalculation
        isSortedDescending = summary.isSortedDescending
        sortedColumn = summary.sortedColumn
    }
}

