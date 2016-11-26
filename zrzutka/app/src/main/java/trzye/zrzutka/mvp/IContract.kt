package trzye.zrzutka.mvp

interface IContract{

    interface IView<out P : IPresenter<*>> {

        var presenterId: Long
        val presenter: P

        fun dismissView()
    }

    abstract class IPresenter<V : IView<IPresenter<V>>> {
        lateinit var view: V
        fun attachView(view: V) {this.view = view}
    }

}




