package pl.edu.pw.jereczem.zrzutka.client.model.friend.secureinformation.contact

import pl.edu.pw.jereczem.zrzutka.client.model.friend.secureinformation.SecureInformation

interface ContactInformation : SecureInformation {
    val type : ContactInformationType
}