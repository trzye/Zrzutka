package trzye.zrzutka.mvp

import trzye.zrzutka.fatclient.mainactivity.MainActivityWaitingRoom
import java.util.*

abstract class PresentersWaitingRoom<P : IContract.IPresenter<*>> protected constructor(){

    abstract fun initPresenter(): P

    val presenters: HashMap<Long, P> = hashMapOf()
    val jobs: Stack< (P) -> Unit > = Stack()

    fun getPresenter(id : Long) : P {
        return presenters[id] ?: addPresenter(id)
    }

    fun addPresenter(id: Long) : P {
        val presenter = initPresenter()
        presenters.put(id, presenter)
        return presenter
    }

    fun addJobForNextPresenter(job: (P) -> Unit){
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