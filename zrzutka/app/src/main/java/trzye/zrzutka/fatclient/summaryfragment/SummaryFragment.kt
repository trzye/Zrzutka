package trzye.zrzutka.fatclient.summaryfragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import trzye.zrzutka.R
import trzye.zrzutka.databinding.ItemDebtBinding
import trzye.zrzutka.fatclient.contributionfragment.AbstractContributionFragment
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.model.ModelProvider
import trzye.zrzutka.model.dto.DebtDTO
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contribution.getDebtList
import trzye.zrzutka.databinding.FragmentSummaryBinding
import android.content.Context.CLIPBOARD_SERVICE
import android.content.ClipData
import android.R.attr.label
import android.content.ClipboardManager
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import android.widget.LinearLayout




class SummaryFragment(dataHolder: ContributionDataHolder?) : AbstractContributionFragment<SummaryFragmentContract.View, SummaryFragmentContract.Presenter>(SummaryFragmentWaitingRoom, dataHolder), SummaryFragmentContract.View {

    constructor() : this(null)

    override val labelId: Int = R.string.tab_summary
    private lateinit var fragmentView: View
    private lateinit var loadingView: Dialog

    lateinit var binding: FragmentSummaryBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        val view = inflater.inflate(R.layout.fragment_summary, null)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)

        binding.actionButton.setOnClickListener { presenter.generateSummaryUrl() }
        binding.switchPreciseMode.setOnCheckedChangeListener { compoundButton, isOn -> presenter.changePreciseMode() }

        binding.whoPaysHeader.setOnClickListener { presenter.orderByWhoPaysHeader() }
        binding.toWhomHeader.setOnClickListener { presenter.orderByToWhomHeader() }
        binding.amountHeader.setOnClickListener { presenter.orderByAmountHeader() }

        binding.summaryRecyclerView.layoutManager = LinearLayoutManager(activity)
        if(binding.summaryRecyclerView.itemAnimator is SimpleItemAnimator){
            (binding.summaryRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        loadingView = ProgressDialog(activity).apply {
            isIndeterminate = true
        }

        super.onCreateView(inflater,container, savedInstanceState)
        presenter.bindData()
        return binding.root
    }

    override fun setPreciseModeSwitchActive() {
        binding.switchPreciseMode.isEnabled = true
    }

    override fun showLoadingView() {
        loadingView = ProgressDialog(activity).apply {
            isIndeterminate = true
            setMessage("Generowanie URL")
            setCancelable(false)
        }
        loadingView.show()
    }

    override fun dismissLoadingView() {
        loadingView.dismiss()
    }

    override fun showConnectionErrorMessage() {
        Toast.makeText(activity, "Problem z połączeniem", Toast.LENGTH_SHORT).show()
    }

    override fun showShareResultDialog(result: Long) {
        val url = "http://${ModelProvider.IP}/zrzutka/$result"
        val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("url", url)
        clipboard.primaryClip = clip
        val view = View.inflate(activity, R.layout.dialog_url, null)
        (view.findViewById(R.id.linkTextView) as TextView).text = url
        AlertDialog.Builder(activity).setPositiveButton("ZAPISZ DO SCHOWKA", { di, i ->
            clipboard.primaryClip = clip
            di.dismiss()
            Toast.makeText(activity, "Skopiowano do schowka", Toast.LENGTH_SHORT).show()
        }).setView(view).create().show()
    }

    override fun setPreciseModeSwitchInactive() {
        binding.switchPreciseMode.isEnabled = true //better to be active always
    }

    override fun bindData(contribution: Contribution) {
        binding.summary = contribution.summary
        binding.summaryRecyclerView.adapter = SummaryAdapter(contribution.getDebtList())
    }

    override fun changeDataSet(contribution: Contribution) {
        (binding.summaryRecyclerView.adapter as SummaryAdapter).apply {
            this.debts = contribution.getDebtList()
            notifyDataSetChanged()
        }
    }

    inner private class SummaryAdapter(var debts: List<DebtDTO>) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun getItemId(position: Int): Long {
            return debts[position].hashCode().toLong()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemDebtBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_debt, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            if(position != debts.size){
                binding.debt = debts[position]
                binding.itemDebtLayout.visibility = View.VISIBLE
            } else {
                binding.itemDebtLayout.visibility = View.INVISIBLE
            }
        }

        override fun getItemCount(): Int = debts.size

        inner class ViewHolder(val binding: ItemDebtBinding) : RecyclerView.ViewHolder(binding.root)
    }

}


