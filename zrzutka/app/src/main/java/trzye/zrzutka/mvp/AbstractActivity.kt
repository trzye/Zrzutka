package trzye.zrzutka.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


abstract class AbstractActivity<P : IContract.IPresenter<V>, V : IContract.IView<P>>(val waitingRoom: PresentersWaitingRoom<P>): AppCompatActivity(), IContract.IView<P>{

    private val PRESENTER_ID = "PRESENTER_ID"

    override var presenterId: Long = waitingRoom.generateId()

    override val presenter: P by lazy {
       waitingRoom.getPresenter(presenterId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterId = savedInstanceState?.getLong(PRESENTER_ID) ?: presenterId
        presenter.attachView(this as V)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putLong(PRESENTER_ID, presenterId)
    }

    override fun dismissView() {
        waitingRoom.removePresenter(presenterId)
        finish()
    }

}
