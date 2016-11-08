package trzye.zrzutka.model.entity

import android.databinding.BaseObservable
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Contribution : BaseObservable() {

    @Id @GeneratedValue val id: Long? = null

    @Column var title: String =""
        set(value) {title = value; notifyChange()}

    @Column(columnDefinition = "DATE") var startDate: Date = Date()
        set(value) {field = value; notifyChange()}

    @Column(columnDefinition = "DATE") var endDate: Date = Date()
        set(value) {field = value; notifyChange()}

    fun getReadableEndDate() : String = SimpleDateFormat("dd.MM.yyyy").format(endDate)
    fun getReadableStartDate() : String = SimpleDateFormat("dd.MM.yyyy").format(startDate)
}