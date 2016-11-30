package pl.edu.pw.ee.jereczem.krs.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "contribution_summaries")
data class ContributionSummary(
        @Id
        @GeneratedValue
        @Column(name = "id")
        val id : Long? = null,

        @Column(name = "jsonData", columnDefinition = "TEXT")
        val jsonData : String = "",

        @Column(name = "creationDate")
        val creationDate : Date = Date()
)