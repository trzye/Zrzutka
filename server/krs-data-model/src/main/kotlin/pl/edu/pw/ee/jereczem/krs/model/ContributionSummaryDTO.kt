package pl.edu.pw.ee.jereczem.krs.model
data class ContributionSummaryDTO(
        val title: String,
        val subtitle: String,
        val preciseMode: String,
        val contributors: List<ContributorDTO> = emptyList(),
        val purchases: List<PurchaseDTO> = emptyList(),
        val debts: List<DebtDTO> = emptyList()
){
    constructor() : this("","", "")
}

data class DebtDTO(
        val whoPays: String,
        val toWhom: String,
        val amount: String
){
    constructor() : this("","", "")
}

data class PurchaseDTO(
        val name: String,
        val price: String,
        val charges: List<ChargeDTO> = emptyList()
){
    constructor() : this("","")
}

data class ChargeDTO(
        val charged: String,
        val paid: String,
        val toPay: String
){
    constructor() : this("","","")
}

data class ContributorDTO(
        val nickname: String,
        val contactInformation: String,
        val paymentInformation: String
){
    constructor() : this("","", "")
}
