package pl.edu.pw.ee.jereczem.krs.rest

import javax.naming.InitialContext

object BeanProvider{

    val context = InitialContext()

    fun <T>provide(beanClass : Class<T>) : T = context.lookup("java:app/krs-logic-business-layer/${beanClass.simpleName}") as T
}
