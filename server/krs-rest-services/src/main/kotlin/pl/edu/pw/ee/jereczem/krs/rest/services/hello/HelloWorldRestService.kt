package pl.edu.pw.ee.jereczem.krs.rest.services.hello

import javax.ws.rs.Path
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/hello_world")
class HelloWorldRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun helloWorld(@Context securityContext: SecurityContext) = "Hello, world!"

}