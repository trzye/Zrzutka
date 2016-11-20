package trzye.zrzutka.androidmvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.mvp.IContract


abstract class AbstractFragment<V : IContract.IView<P>, P : IContract.IPresenter<V>>(val waitingRoom: PresentersWaitingRoom<P>): Fragment(), IContract.IView<P> {

    private val PRESENTER_ID = "PRESENTER_ID"

    override var presenterId: Long = waitingRoom.generateId()

    override val presenter: P by lazy {
       waitingRoom.getPresenter(presenterId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        presenterId = savedInstanceState?.getLong(PRESENTER_ID) ?: presenterId
        presenter.attachView(this as V)
        return view
    }

    override fun onStart() {
        super.onStart()
        waitingRoom.runAllJobs(presenter)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putLong(PRESENTER_ID, presenterId)
    }

    override fun dismissView() {
        waitingRoom.removePresenter(presenterId)
        activity.finish()
    }

}
