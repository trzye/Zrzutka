package trzye.zrzutka.fatclient.purchasedialog

import android.app.Activity
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import trzye.zrzutka.R
import trzye.zrzutka.databinding.DialogPurchaseBinding
import trzye.zrzutka.databinding.ItemEditChargeBinding
import trzye.zrzutka.model.entity.purchase.Purchase

class PurchaseDialog(private val activity: Activity) : Dialog(activity), PurchaseDialogContract.View {

    override var presenterId: Long = 0

    override var presenter: PurchaseDialogContract.Presenter = PurchaseDialogPresenter()

    lateinit var view: View
    lateinit var binding: DialogPurchaseBinding

    override fun start(presenter: PurchaseDialogContract.Presenter) {
        show()
        this.presenter = presenter
        this.presenter.attachView(this)
        this.presenter.show()
    }

//    override fun startAsCreateNewPurchaseDialog(actionOnOKClicked: (Purchase) -> Unit) {
//        show()
//        presenter.createNewPurchase(actionOnOKClicked)
//    }

    override fun startAsEditExistingPurchaseDialog(purchase: Purchase, actionOnSuccess: (Purchase) -> Unit, actionOnDismiss: () -> Unit) {
        show()
        presenter.editPurchaseData(purchase, actionOnSuccess, actionOnDismiss)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_purchase, null, false)
        view = binding.root
        setContentView(view)
        presenter.attachView(this)
        binding.actionSavePurchase.setOnClickListener{presenter.okClicked()}
        binding.actionSplitCosts.setOnClickListener { presenter.splitToPayCost() }
        binding.chargesListItem.layoutManager = LinearLayoutManager(activity)
    }

//    override fun getPriceStringFromInput(): String = binding.purchaseSubtitle.text.toString()

    override fun dismissView() = dismiss()

    override fun bindData(dataHolder: PurchaseDialogDataHolder) {
        binding.dataHolder = dataHolder
        binding.chargesListItem.adapter = ChargesAdapter(dataHolder)
    }

    override fun showEmptyTitleError() {
        Snackbar.make(view, R.string.empty_title_warning, Snackbar.LENGTH_SHORT).apply {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
        }.show()
    }

    override fun getPurchaseTitle() = binding.purchaseTitle.text.toString()

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.backPressed()
    }

    override fun show() {
        super.show()
        setCanceledOnTouchOutside(false)
    }

    inner private class ChargesAdapter(var purchase: PurchaseDialogDataHolder) : RecyclerView.Adapter<ChargesAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemEditChargeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_edit_charge, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            binding.chargeDataHolder = purchase.charges[position]
        }

        override fun getItemCount(): Int = purchase.charges.size

        inner class ViewHolder(val binding: ItemEditChargeBinding) : RecyclerView.ViewHolder(binding.root)
    }

}