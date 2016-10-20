package pl.edu.pw.ee.jereczem.krs.dao

import pl.edu.pw.ee.jereczem.krs.model.User
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
open class UserDAO {

    @PersistenceContext(unitName = "krs-persistence-unit")
    lateinit private var entityManager : EntityManager

    open fun getUserBy(username: String): User? {
        return entityManager
                .createQuery("select u from User u where u.username = :username", User::class.java)
                .setParameter("username", username)
                .singleResult
    }
}