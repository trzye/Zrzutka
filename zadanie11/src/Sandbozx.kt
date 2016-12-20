import java.io.IOException
import java.util.*

/**
 * Created by trzye on 19.12.2016.
 */

data class Czlowiek(var imie: String, val mama: Czlowiek?)

fun stworzenieSwiata(){
    val ewa = Czlowiek(imie = "Ewa", mama = null)
    val kain = Czlowiek("Kain", ewa)
    val mamaAbla: Czlowiek =
            if( kain.mama != null ) kain.mama else throw IllegalStateException()
}

fun Double.toReadablePriceString() = String.format( "%.2f", this )

fun getContributorsToNeededPaymentMap(price: Double, contributors: Int) : Map<Int, Double> {
    println("price: ${price.toReadablePriceString()}\n contributors number: $contributors")
    val divide = {
        d : Double, i: Int ->
        if (i == 0)
            throw IOException("Can't divide by 0")
        else
            d / i
    }
    val resul


}

fun View?.setOnCLickListener(actions: List<() -> Unit>){
    if(this == null){
        throw IOException("View doesn't exists")
    }
    this.setOnClickListener { actions.forEach { it() } }
}

fun main(args: Array<String>) {
    val actions: MutableList<() -> Unit> = LinkedList()
    actions.add({ println("Akcja1") })
    actions.add({ println("Akcja2") })

    val view: View? = null
    view.setOnCLickListener(actions)
}

class View {
    fun setOnClickListener(any: Any) {

    }

}



