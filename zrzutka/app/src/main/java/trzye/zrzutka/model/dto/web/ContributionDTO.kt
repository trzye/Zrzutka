package trzye.zrzutka.model.dto.web

import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contribution.getDebtList
import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.model.entity.purchase.Purchase

data class ContributionDTO private constructor(
        val title: String,
        val subtitle: String,
        val preciseMode: String,
        val contributors: List<ContributorDTO>,
        val purchases: List<PurchaseDTO>,
        val debts: List<DebtDTO>
){
    constructor(contribution: Contribution) : this(
            title = contribution.title,
            subtitle = contribution.getReadableDateRanges(),
            preciseMode = if(contribution.summary.preciseCalculation) "true" else "false",
            debts = contribution.getDebtList().flatMap { listOf(DebtDTO(it)) },
            contributors = contribution.contributors.flatMap { listOf(ContributorDTO(it.friend)) },
            purchases = contribution.purchases.flatMap { listOf(PurchaseDTO(it)) }
    )
}

data class DebtDTO private constructor(
        val whoPays: String,
        val toWhom: String,
        val amount: String
){
    constructor(debtDTO: trzye.zrzutka.model.dto.DebtDTO) : this(
            whoPays = debtDTO.whoPays.nickname,
            toWhom = debtDTO.toWhom.nickname,
            amount = debtDTO.getReadableAmountToPay()
    )
}

data class PurchaseDTO private constructor(
        val name: String,
        val price: String,
        val charges: List<ChargeDTO>
){
    constructor(purchase: Purchase) : this(
            name = purchase.name,
            price = purchase.getReadablePrice(),
            charges = purchase.charges.flatMap { listOf(ChargeDTO(it)) }
    )
}

data class ChargeDTO private constructor(
        val charged: String,
        val paid: String,
        val toPay: String
){
    constructor(charge: Charge) : this(
            charged = charge.charged?.friend?.nickname ?: "",
            paid = charge.getReadableAmountPaid(),
            toPay = charge.getReadableAmountToPay()
    )
}

data class ContributorDTO private constructor(
        val nickname: String,
        val contactInformation: String,
        val paymentInformation: String
){
    constructor(friend: Friend) : this(
            nickname = friend.nickname,
            contactInformation =  friend.contactInformation,
            paymentInformation = friend.paymentInformation
    )
}
