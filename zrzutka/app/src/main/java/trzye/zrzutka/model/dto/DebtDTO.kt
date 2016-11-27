package trzye.zrzutka.model.dto

import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.friend.Friend

class DebtDTO(val whoPays: Friend, val toWhom: Friend, val amount: Double){

    fun getReadableAmountToPay() = amount.toReadablePriceString()

}
