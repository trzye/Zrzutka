package trzye.zrzutka.fatclient.purchasedialog

import android.app.Activity
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import trzye.zrzutka.R
import trzye.zrzutka.databinding.DialogPurchaseBinding
import trzye.zrzutka.model.entity.purchase.Purchase

class PurchaseDialog(activity: Activity) : Dialog(activity), PurchaseDialogContract.View {

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

    override fun startAsCreateNewPurchaseDialog(actionOnOKClicked: (Purchase) -> Unit) {
        show()
        presenter.createNewPurchase(actionOnOKClicked)
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
    }

    override fun getPriceStringFromInput(): String = binding.purchaseSubtitle.text.toString()

    override fun dismissView() = dismiss()

    override fun bindData(dataHolder: PurchaseDialogDataHolder) {
        binding.dataHolder = dataHolder
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

}