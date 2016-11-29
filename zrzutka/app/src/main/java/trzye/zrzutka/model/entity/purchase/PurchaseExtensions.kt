package trzye.zrzutka.model.entity.purchase

fun Purchase.validate() : PurchaseValidationStatus{
    val price = this.price
    val sumToPay = this.charges.flatMap { listOf(it.amountToPay) }.sum()
    val sumPaid = this.charges.flatMap { listOf(it.amountPaid) }.sum()
    if(sumToPay != price) return PurchaseValidationStatus.WRONG_TO_PAY_SUM
    if(sumPaid != price) return PurchaseValidationStatus.WRONG_PAID_SUM
    return PurchaseValidationStatus.OK
}

private fun Double.equalsRounded(other: Double): Boolean {
    val thisRounded = (this * 100).toInt()
    val otherRounded = (other * 100).toInt()
    return((thisRounded > otherRounded - 1) && (thisRounded < otherRounded + 1))
}

enum class PurchaseValidationStatus {
    OK,
    WRONG_PAID_SUM,
    WRONG_TO_PAY_SUM
}
