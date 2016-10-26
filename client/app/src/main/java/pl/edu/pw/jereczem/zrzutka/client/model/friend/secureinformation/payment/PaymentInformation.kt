package pl.edu.pw.jereczem.zrzutka.client.model.friend.secureinformation.payment

import pl.edu.pw.jereczem.zrzutka.client.model.friend.secureinformation.SecureInformation

interface PaymentInformation : SecureInformation {
    val type : PaymentInformationType
}