package pl.edu.pw.ee.jereczem.krs.dao

import pl.edu.pw.ee.jereczem.krs.model.ContributionSummary
import pl.edu.pw.ee.jereczem.krs.model.ContributionSummaryDTO
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

@Stateless
open class ContributionSummaryDAO {

    @PersistenceContext(unitName = "krs-persistence-unit")
    lateinit private var entityManager : EntityManager

    open fun getContributionSummary(id: Long): ContributionSummary? {
        try {
            return entityManager
                    .createQuery(
                            "select c from ContributionSummary c where c.id = :id",
                            ContributionSummary::class.java)
                    .setParameter("id", id)
                    .singleResult
        } catch (e: NoResultException){
            return null
        }
    }

    open fun saveContributionSummary(summary: ContributionSummary):Long {
        entityManager.apply {
            persist(summary)
            flush()
        }
        return summary.id ?: throw IllegalArgumentException("Can't persist $summary")
    }
}