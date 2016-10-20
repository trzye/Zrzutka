package pl.edu.pw.ee.jereczem.krs.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue
        @Column(name = "id")
        val id : Long? = null,

        @Column(name = "username")
        val username : String = "",

        @Column(name = "password")
        val password : String = ""
)