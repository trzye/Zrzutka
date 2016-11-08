package trzye.zrzutka.model.entity

import android.databinding.BaseObservable
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Contribution private constructor(@Id @GeneratedValue val id: Long? = null) : BaseObservable(), Cloneable {

    constructor() : this(null)

    @Column var title: String =""
        set(value) {field = value; notifyChange()}

    @Column(columnDefinition = "DATE") var startDate: Date = Date()
        set(value) {field = value; notifyChange()}

    @Column(columnDefinition = "DATE") var endDate: Date = Date()
        set(value) {field = value; notifyChange()}

    fun getReadableEndDate() : String = SimpleDateFormat("dd.MM.yyyy").format(endDate)

    fun getReadableStartDate() : String = SimpleDateFormat("dd.MM.yyyy").format(startDate)

    override public fun clone(): Contribution {
        val clone = Contribution(id)
        clone.title = title
        clone.endDate = Date(endDate.time)
        clone.startDate = Date(startDate.time)
        return clone
    }

}

