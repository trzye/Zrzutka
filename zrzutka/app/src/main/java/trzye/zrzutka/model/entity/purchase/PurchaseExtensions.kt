package trzye.zrzutka.model.entity.purchase

fun Purchase.validate() : PurchaseValidationStatus{
    return PurchaseValidationStatus.OK
}

enum class PurchaseValidationStatus {
    OK,
    NOT_OK
}
