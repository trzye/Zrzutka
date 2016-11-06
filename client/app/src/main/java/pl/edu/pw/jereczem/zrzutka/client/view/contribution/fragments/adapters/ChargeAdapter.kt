package pl.edu.pw.jereczem.zrzutka.client

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Charge
import pl.edu.pw.jereczem.zrzutka.client.view.common.extensions.toReadablePriceString

class ChargeAdapter(val charges: List<Charge>) : RecyclerView.Adapter<ChargeAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return charges.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.charge_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val charge = charges[position]
        holder.chargeContributor.text = charge.charged?.friend?.getShowingName()
        holder.chargeContributorPaid.text = charge.amountPaid.toReadablePriceString()
        holder.chargeContributorToPay.text = charge.amountToPay.toReadablePriceString()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val chargeContributor = view.findViewById(R.id.chargeContributor) as TextView
        val chargeContributorPaid = view.findViewById(R.id.chargeContributorPaid) as TextView
        val chargeContributorToPay = view.findViewById(R.id.chargeContributorToPay) as TextView
    }

}