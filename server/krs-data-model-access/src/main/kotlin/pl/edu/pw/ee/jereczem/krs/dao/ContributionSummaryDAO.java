package pl.edu.pw.ee.jereczem.krs.dao;

import pl.edu.pw.ee.jereczem.krs.model.ContributionSummary;
import pl.edu.pw.ee.jereczem.krs.model.ContributionSummaryDTO;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class ContributionSummaryDAO {

    @PersistenceContext(unitName = "krs-persistence-unit")
    private EntityManager entityManager;

    public ContributionSummary getContributionSummary(Long id) {
        try {
            return entityManager
                    .createQuery(
                            "select c from ContributionSummary c where c.id = :id",
                            ContributionSummary.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public Long saveContributionSummary(ContributionSummary summary) throws IllegalArgumentException {
        entityManager.persist(summary);
        entityManager.flush();
        if(summary.getId() != null)
            return summary.getId();
        else
            throw new IllegalArgumentException("Can't persist $summary");
    }
}