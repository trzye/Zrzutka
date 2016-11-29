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

    override fun makeRedUnderPrice() {
        binding.purchaseSubtitle.setBackgroundResource(R.color.colorErrorTransparent)
    }

    override fun notifyChange(position: Int) {
        binding.chargesListItem.adapter.notifyItemChanged(position)
    }


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

    override fun resetSubtitleError() {
        binding.purchaseSubtitle.setBackgroundResource(R.color.transparent)
    }

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
        binding.purchaseSubtitle.setTransparentBackgroundOnTouch()
    }

    private fun View.setTransparentBackgroundOnTouch() {
        this.setOnTouchListener { view, motionEvent ->
            this.setBackgroundResource(R.color.transparent)
            this.onTouchEvent(motionEvent)
        }
    }

    override fun dismissView() = dismiss()

    override fun bindData(dataHolder: PurchaseDialogDataHolder) {
        binding.dataHolder = dataHolder
        binding.chargesListItem.adapter = ChargesAdapter(dataHolder)
    }

    override fun showEmptyPurchaseNameError() {
        showErrorSnackBar(R.string.empty_purchase_name_warning)
    }

    override fun showWrongPaidSumFormatError() {
        showErrorSnackBar(R.string.wrong_sum_paid_warning)
    }

    override fun showWrongPriceFormatError() {
        showErrorSnackBar(R.string.wrong_format_cost_warning)
    }

    override fun showWrongToPayFormatError() {
        showErrorSnackBar(R.string.wrong_format_to_pay_warning)
    }

    override fun showWrongToPaySumFormatError() {
        showErrorSnackBar(R.string.wrong_sum_to_pay_warning)
    }

    private fun showErrorSnackBar(stringResource: Int) {
        Snackbar.make(view, stringResource, Snackbar.LENGTH_SHORT).apply {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
        }.show()
    }

    override fun showWrongPaidFormatError() {
        showErrorSnackBar(R.string.wrong_format_paid_warning)
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
            if(purchase.charges[position].isWrongPaid)
                binding.chargeContributorPaid.setBackgroundResource(R.color.colorErrorTransparent)
            else
                binding.chargeContributorPaid.setBackgroundResource(R.color.transparent)
            if(purchase.charges[position].isWrongToPay)
                binding.chargeContributorToPay.setBackgroundResource(R.color.colorErrorTransparent)
            else
                binding.chargeContributorToPay.setBackgroundResource(R.color.transparent)
            binding.chargeContributorPaid.setTransparentBackgroundOnTouch()
            binding.chargeContributorToPay.setTransparentBackgroundOnTouch()
        }

        override fun getItemCount(): Int = purchase.charges.size

        inner class ViewHolder(val binding: ItemEditChargeBinding) : RecyclerView.ViewHolder(binding.root)
    }

}