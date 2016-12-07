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
        val resultDebtList: MutableList<DebtDTO> = mutableListOf()
        val debts = getSortedDebts(contribution)

        while (debts.find { it.debt != 0L } != null){
            debts.sortByDescending { it.debt }
            val debt = DebtDTO(
                    debts.first().contributor.friend
                    , debts.last().contributor.friend
                    , if(debts.first().debt > (debts.last().debt * -1)) (debts.last().debt * -1) else debts.first().debt
            )
            resultDebtList.add(debt)
            debts.first().debt -= debt.amount
            debts.last().debt += debt.amount
        }

//        resultDebtList.addAll(debts.flatMap { listOf(DebtDTO(it.contributor.friend, it.contributor.friend, it.debt)) })
        return resultDebtList
    }

    inner class DebtData(val contributor: Contributor, var debt: Long)

    private fun getSortedDebts(contribution: Contribution): MutableList<DebtData> {
        val chargedAndDebt = HashMap<Contributor, Long>().apply {
            putAll(contribution.contributors.map { it to 0L })
        }
        chargedAndDebt.entries.forEach {
            entry ->
            entry.key.charges.forEach {
                chargedAndDebt.put(entry.key, entry.value + it.amountToPay - it.amountPaid)
            }
        }
        val sortedDebts = sort(chargedAndDebt).flatMap { listOf(DebtData(it.key, it.value)) }
        return ArrayList<DebtData>().apply { addAll(sortedDebts) }
    }

    private fun sort(chargedAndDebt: MutableMap<Contributor, Long>) = chargedAndDebt.entries.sortedBy { it.value }
}

private interface SummaryCalculator {
    fun calculateDebtList(contribution: Contribution): MutableList<DebtDTO>
}
