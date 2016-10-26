package pl.edu.pw.jereczem.zrzutka.client.model.friend.secureinformation

interface SecureInformation{
    val information: String
    fun validate(): Boolean
}