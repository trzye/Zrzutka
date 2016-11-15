package trzye.zrzutka.mvp

interface IContract{

    interface IView<out P : IPresenter<*>> {

        var presenterId: Long
        val presenter: P

        fun dismissView()

    }

    interface IPresenter<V : IView<IPresenter<V>>> {
        fun attachView(view: V)
    }

}




