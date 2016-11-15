package trzye.zrzutka.mvp

import java.util.*

abstract class PresentersWaitingRoom<P : IContract.IPresenter<*>> protected constructor(){

    abstract fun initPresenter(): P

    val presenters: HashMap<Long, P> = hashMapOf()

    fun getPresenter(id : Long) : P {
        return presenters[id] ?: addPresenter(id)
    }

    fun addPresenter(id: Long) : P {
        val presenter = initPresenter()
        presenters.put(id, presenter)
        return presenter
    }

    fun removePresenter(id: Long) {
        presenters.remove(id)
    }

    fun generateId() : Long {
        return (presenters.keys.max() ?: 0) + 1
    }

}