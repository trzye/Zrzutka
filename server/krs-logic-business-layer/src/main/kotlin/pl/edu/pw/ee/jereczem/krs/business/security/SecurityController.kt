package pl.edu.pw.ee.jereczem.krs.business.security

import pl.edu.pw.ee.jereczem.krs.dao.UserDAO
import javax.ejb.EJB
import javax.ejb.Stateless

@Stateless
open class SecurityController {

    @EJB
    lateinit private var userDAO : UserDAO

    open fun areCredentialsCorrect(username: String, password: String): Boolean {
        val user = userDAO.getUserBy(username)
        if(user != null)
            return user.password == password
        return false
    }

}

