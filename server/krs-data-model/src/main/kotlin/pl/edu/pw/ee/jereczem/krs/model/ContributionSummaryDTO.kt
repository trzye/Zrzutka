package pl.edu.pw.ee.jereczem.krs.model
data class ContributionSummaryDTO private constructor(
        val title: String,
        val subtitle: String,
        val preciseMode: String,
        val contributors: List<ContributorDTO> = emptyList(),
        val purchases: List<PurchaseDTO> = emptyList(),
        val debts: List<DebtDTO> = emptyList()
){
    constructor() : this("","", "")
}

data class DebtDTO private constructor(
        val whoPays: String,
        val toWhom: String,
        val amount: String
){
    constructor() : this("","", "")
}

data class PurchaseDTO private constructor(
        val name: String,
        val price: String,
        val charges: List<ChargeDTO> = emptyList()
){
    constructor() : this("","")
}

data class ChargeDTO private constructor(
        val charged: String,
        val paid: String,
        val toPay: String
){
    constructor() : this("","","")
}

data class ContributorDTO private constructor(
        val nickname: String,
        val contactInformation: String,
        val paymentInformation: String
){
    constructor() : this("","", "")
}
