package trzye.zrzutka.view

import android.content.Context
import android.databinding.BaseObservable

interface IView<in T : BaseObservable> {
    fun getContext() : Context
    fun bindData(observable: T)
}