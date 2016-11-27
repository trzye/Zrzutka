package trzye.zrzutka.model.entity.contribution

import trzye.zrzutka.model.dto.DebtDTO
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.summary.SortedColumn.*
import java.util.*

fun Contribution.getDebtList() : List<DebtDTO>{
    val calculator: SummaryCalculator = SimpleSummaryCalculator()
    val debtList = calculator.calculateDebtList(this)
    return sortResult(debtList)
}

private fun Contribution.sortResult(debtList: List<DebtDTO>) =
    if (summary.isSortedDescending) sortDescending(debtList) else sortAscending(debtList)

private fun Contribution.sortAscending(debtList: List<DebtDTO>) = when (summary.sortedColumn) {
        WHO_PAYS -> debtList.sortedBy { it.whoPays.getShowingName() }
        TO_WHOM -> debtList.sortedBy { it.toWhom.getShowingName() }
        AMOUNT -> debtList.sortedBy { it.amount }
}

private fun Contribution.sortDescending(debtList: List<DebtDTO>) = when (summary.sortedColumn) {
    WHO_PAYS -> debtList.sortedByDescending { it.whoPays.getShowingName() }
    TO_WHOM -> debtList.sortedByDescending { it.toWhom.getShowingName() }
    AMOUNT -> debtList.sortedByDescending { it.amount }
}

//TODO
private class SimpleSummaryCalculator : SummaryCalculator {

    override fun calculateDebtList(contribution: Contribution): List<DebtDTO> {
        val debtList: MutableList<DebtDTO> = mutableListOf()
        val sortedDebts = getSortedDebts(contribution)
        debtList.addAll(sortedDebts.flatMap { listOf(DebtDTO(it.first.friend, it.first.friend, it.second)) })
        return debtList
    }

    private fun getSortedDebts(contribution: Contribution): List<Pair<Contributor, Double>> {
        val chargedAndDebt = HashMap<Contributor, Double>().apply {
            putAll(contribution.contributors.map { it to 0.0 })
        }
        chargedAndDebt.entries.forEach {
            entry ->
            entry.key.charges.forEach {
                chargedAndDebt.put(entry.key, entry.value + it.amountToPay - it.amountPaid)
            }
        }
        val sortedDebts = sort(chargedAndDebt).flatMap { listOf(Pair(it.key, it.value)) }
        return ArrayList<Pair<Contributor, Double>>().apply { addAll(sortedDebts) }
    }

    private fun sort(chargedAndDebt: MutableMap<Contributor, Double>) = chargedAndDebt.entries.sortedBy { it.value }
}

private interface SummaryCalculator {
    fun calculateDebtList(contribution: Contribution): List<DebtDTO>
}
