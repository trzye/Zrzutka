package trzye.zrzutka.model.entity.contribution

import trzye.zrzutka.model.dto.DebtDTO
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.summary.SortedColumn
import trzye.zrzutka.model.entity.summary.SortedColumn.*
import java.util.*

fun Contribution.getDebtList() : List<DebtDTO>{
    val calculator: SummaryCalculator = SimpleSummaryCalculator()
    var debtList = calculator.calculateDebtList(this)
    debtList = preciseCalculation(debtList)
    return sortResult(summary.sortedColumn, summary.isSortedDescending, debtList)
}

private fun Contribution.preciseCalculation(debtList: MutableList<DebtDTO>): MutableList<DebtDTO> {
    var debtList1 = debtList
    if (!summary.preciseCalculation) {
        debtList1.forEach {
            val rest = it.amount % 100
            if (rest > 49) it.amount += 100 - rest
            else it.amount -= rest
        }
        debtList1 = debtList1.filter { it.amount > 0 }.toMutableList()
    }
    return debtList1
}

private fun sortResult(sortedColumn: SortedColumn, isSortedDescending: Boolean, debtList: List<DebtDTO>) =
    if (isSortedDescending) sortDescending(sortedColumn, debtList) else sortAscending(sortedColumn, debtList)

private fun sortAscending(sortedColumn: SortedColumn, debtList: List<DebtDTO>) = when (sortedColumn) {
        WHO_PAYS -> debtList.sortedBy { it.whoPays.getShowingName() }
        TO_WHOM -> debtList.sortedBy { it.toWhom.getShowingName() }
        AMOUNT -> debtList.sortedBy { it.amount }
}

private fun sortDescending(sortedColumn: SortedColumn, debtList: List<DebtDTO>) = when (sortedColumn) {
    WHO_PAYS -> debtList.sortedByDescending { it.whoPays.getShowingName() }
    TO_WHOM -> debtList.sortedByDescending { it.toWhom.getShowingName() }
    AMOUNT -> debtList.sortedByDescending { it.amount }
}

//TODO
private class SimpleSummaryCalculator : SummaryCalculator {

    override fun calculateDebtList(contribution: Contribution): MutableList<DebtDTO> {
        val debtList: MutableList<DebtDTO> = mutableListOf()
        val sortedDebts = getSortedDebts(contribution)
        debtList.addAll(sortedDebts.flatMap { listOf(DebtDTO(it.first.friend, it.first.friend, it.second)) })
        return debtList
    }

    private fun getSortedDebts(contribution: Contribution): List<Pair<Contributor, Long>> {
        val chargedAndDebt = HashMap<Contributor, Long>().apply {
            putAll(contribution.contributors.map { it to 0L })
        }
        chargedAndDebt.entries.forEach {
            entry ->
            entry.key.charges.forEach {
                chargedAndDebt.put(entry.key, entry.value + it.amountToPay - it.amountPaid)
            }
        }
        val sortedDebts = sort(chargedAndDebt).flatMap { listOf(Pair(it.key, it.value)) }
        return ArrayList<Pair<Contributor, Long>>().apply { addAll(sortedDebts) }
    }

    private fun sort(chargedAndDebt: MutableMap<Contributor, Long>) = chargedAndDebt.entries.sortedBy { it.value }
}

private interface SummaryCalculator {
    fun calculateDebtList(contribution: Contribution): MutableList<DebtDTO>
}
