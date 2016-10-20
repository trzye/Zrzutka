package pl.edu.pw.ee.jereczem.krs.dao

import pl.edu.pw.ee.jereczem.krs.model.User
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Startup
@Singleton
open class DatabaseStartup {

    @PersistenceContext(unitName = "krs-persistence-unit")
    lateinit private var entityManager : EntityManager

    @PostConstruct
    open fun init(){
        val user = User(username = "test", password = "pass")
        entityManager.persist(user)
    }

}