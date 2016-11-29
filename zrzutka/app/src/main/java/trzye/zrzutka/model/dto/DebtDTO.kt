package trzye.zrzutka.model.dto

import trzye.zrzutka.common.extensions.toMoneyDouble
import trzye.zrzutka.common.extensions.toReadablePriceString
import trzye.zrzutka.model.entity.friend.Friend

class DebtDTO(val whoPays: Friend, val toWhom: Friend, var amount: Long){

    fun getReadableAmountToPay() = amount.toMoneyDouble().toReadablePriceString()

}
