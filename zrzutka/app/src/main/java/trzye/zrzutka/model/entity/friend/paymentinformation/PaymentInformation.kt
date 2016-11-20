package trzye.zrzutka.model.entity.friend.paymentinformation

import pl.edu.pw.jereczem.zrzutka.client.model.friend.secureinformation.SecureInformation


interface PaymentInformation : SecureInformation {
    val type : PaymentInformationType
}