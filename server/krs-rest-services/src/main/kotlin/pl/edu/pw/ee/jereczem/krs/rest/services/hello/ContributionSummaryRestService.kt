package pl.edu.pw.ee.jereczem.krs.rest.services.hello

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/")
class ContributionSummaryRestService {

    @GET
    @Path("/{summaryId}")
    @Produces(MediaType.TEXT_HTML)
    fun getSummaryPage(@PathParam("summaryId") summaryId: Long) = "Hello $summaryId"

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    fun saveSummary(contribution: ContributionDTO){
        println(contribution.toString())
    }

}