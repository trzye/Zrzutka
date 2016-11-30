package pl.edu.pw.ee.jereczem.krs.business

import com.google.gson.Gson
import pl.edu.pw.ee.jereczem.krs.dao.ContributionSummaryDAO
import pl.edu.pw.ee.jereczem.krs.model.ContributionSummary
import pl.edu.pw.ee.jereczem.krs.model.ContributionSummaryDTO
import java.util.logging.Logger
import javax.ejb.EJB
import javax.ejb.Stateless

@Stateless
open class ContributionSummaryController {

    @EJB
    lateinit private var contributionSummaryDAO : ContributionSummaryDAO

    open val gson = Gson()
    open val logger = Logger.getLogger(ContributionSummaryController::class.java.simpleName)

    open fun getContributionSummaryBy(id: Long) : ContributionSummaryDTO? {
        val summary = contributionSummaryDAO.getContributionSummary(id)
        if(summary != null )
            return gson.fromJson(summary.jsonData, ContributionSummaryDTO::class.java)
        else
            logger.info { "There isn't summary with id $id" }
        return null
    }

    open fun saveContributionSummary(summary: ContributionSummaryDTO) : Long {
        val newContributionSummary = ContributionSummary(jsonData = gson.toJson(summary))
        return contributionSummaryDAO.saveContributionSummary(newContributionSummary)
    }

}

