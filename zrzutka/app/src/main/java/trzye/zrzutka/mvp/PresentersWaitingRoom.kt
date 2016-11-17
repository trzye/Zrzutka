package trzye.zrzutka.mvp

import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class PresentersWaitingRoom<P : IContract.IPresenter<*>> protected constructor(){

    abstract fun initPresenter(): P

    val presenters: ConcurrentHashMap<Long, P> = ConcurrentHashMap()
    val jobs: Stack< (P) -> Any > = Stack()

    fun getPresenter(id : Long) : P {
        return presenters[id] ?: addPresenter(id)
    }

    fun addPresenter(id: Long) : P {
        val presenter = initPresenter()
        presenters.put(id, presenter)
        return presenter
    }

    fun addJobForNextPresenter(job: (P) -> Any ){
        jobs.add(job)
    }

    fun removePresenter(id: Long) {
        presenters.remove(id)
    }

    fun generateId() : Long {
        return (presenters.keys.max() ?: 0) + 1
    }

    fun runAllJobs(presenter: P) {
        while (jobs.isNotEmpty()) {
            jobs.pop().invoke(presenter)
        }
    }

}