package trzye.zrzutka.model.entity.friend.contactinformation

import pl.edu.pw.jereczem.zrzutka.client.model.friend.secureinformation.SecureInformation

interface ContactInformation : SecureInformation {
    val type : ContactInformationType
}