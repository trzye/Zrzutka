package trzye.zrzutka.fatclient.contributionsfragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Toast
import trzye.zrzutka.R
import trzye.zrzutka.androidmvp.AbstractFragment
import trzye.zrzutka.databinding.ItemContributionBinding
import trzye.zrzutka.fatclient.contributionactivity.ContributionActivity
import trzye.zrzutka.fatclient.contributionactivity.ContributionActivityContract
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialog
import trzye.zrzutka.fatclient.contributiondialog.ContributionDialogContract
import trzye.zrzutka.fatclient.contributionsfragment.ContributionsFragmentContract.Presenter
import trzye.zrzutka.model.dto.ContributionsDTO

class ContributionsFragment() : AbstractFragment<ContributionsFragmentContract.View, Presenter>(ContributionsFragmentWaitingRoom), ContributionsFragmentContract.View {

    lateinit var toolbar: Toolbar
    private lateinit var fragmentView: View
    private lateinit var snackbar: Snackbar
    private lateinit var contributionsRecyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater,container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_contributions, null)

        fragmentView = view
        snackbar = makeContributionRemovedInfoSnackBar()

        val actionButton = view.findViewById(R.id.actionButton) as FloatingActionButton
        actionButton.setOnClickListener { presenter.createNewContribution() }

        setHasOptionsMenu(true)
        toolbar = activity.findViewById(R.id.toolbar) as Toolbar
        activity.title = resources.getString(R.string.menu_contribution_list)
        toolbar.setOnMenuItemClickListener { onMenuOptionsItemSelected(it) }

        contributionsRecyclerView = view.findViewById(R.id.contributionsRecyclerView) as RecyclerView
        contributionsRecyclerView.layoutManager = LinearLayoutManager(activity)

        presenter.startDialogIfExists()
        presenter.bindData()
        return view
    }

    override fun dataSetChanged(contributionsDTO: ContributionsDTO) {
        (contributionsRecyclerView.adapter as ContributionsAdapter).apply {
            this.contributionsDTO = contributionsDTO
            notifyDataSetChanged()
        }
    }

    override fun notifyContributionAdded(position: Int, listSize: Int) {
        contributionsRecyclerView.adapter.apply {
            notifyItemInserted(listSize)
            notifyItemRangeChanged(position, listSize)
        }
        contributionsRecyclerView.scrollToPosition(position)
    }

    override fun notifyContributionChanged(position: Int) {
        contributionsRecyclerView.adapter.apply {
            notifyItemChanged(position)
        }
    }

    override fun bindData(contributionsDTO: ContributionsDTO) {
        contributionsRecyclerView.adapter = ContributionsAdapter(contributionsDTO)
    }

    override fun showOneContributionRemovedInfo() {
        snackbar = makeContributionRemovedInfoSnackBar()
        snackbar.show()
    }

    override fun showContributionsRemovedInfo() {
        snackbar = makeContributionsRemovedInfoSnackBar()
        snackbar.show()
    }

    override fun hideInformationAboutRemovingContribution() {
        snackbar.dismiss()
    }

    override fun getContributionActivityView(): ContributionActivityContract.View {
        return ContributionActivity(activity)
    }

    override fun showMergeAtLeastTwoContributions() {
        Toast.makeText(activity, "Wybierz conajmniej dwie zrzutki", Toast.LENGTH_SHORT).show()
    }

    private fun makeContributionRemovedInfoSnackBar() : Snackbar {
        return Snackbar.make(fragmentView, R.string.contribution_deleted, Snackbar.LENGTH_SHORT).apply {
            setAction(R.string.undo, { presenter.undoRemovingContributions() })
        }
    }

    private fun makeContributionsRemovedInfoSnackBar() : Snackbar {
        return Snackbar.make(fragmentView, R.string.contributions_deleted, Snackbar.LENGTH_SHORT).apply {
            setAction(R.string.undo, { presenter.undoRemovingContributions() })
        }
    }

    override fun setToolbarForEdition() {
        toolbar.menu.findItem(R.id.menu_delete).isVisible = true
        toolbar.menu.findItem(R.id.menu_merge).isVisible = true
    }

    override fun setToolbarForStandardMode() {
        toolbar.menu.findItem(R.id.menu_delete).isVisible = false
        toolbar.menu.findItem(R.id.menu_merge).isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_contributions_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        presenter.setToolbar()
    }

    override fun getContributionDialogView(): ContributionDialogContract.View {
        return ContributionDialog(activity)
    }

    fun onMenuOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete -> presenter.removeCheckedContributions()
            R.id.menu_merge -> presenter.mergeCheckedContributions()
        }
        return true
    }

    inner private class ContributionsAdapter(var contributionsDTO: ContributionsDTO) : RecyclerView.Adapter<ContributionsAdapter.ViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun getItemId(position: Int): Long {
            if(position != contributionsDTO.contributions.size){
                return contributionsDTO.contributions[position].hashCode().toLong()
            } else return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemContributionBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_contribution, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding = holder.binding
            if(position != contributionsDTO.contributions.size){
                binding.contribution = contributionsDTO.contributions[position]
                binding.itemContributionId.visibility = View.VISIBLE
                if(contributionsDTO.checked[position]){
                  binding.checked.visibility = View.VISIBLE
                } else {
                    binding.checked.visibility = View.INVISIBLE
                }
                binding.itemContributionId.setOnLongClickListener { presenter.changeCheckedOption(holder.adapterPosition); true }
                binding.itemContributionId.setOnClickListener { presenter.openContributionInReadMode(holder.adapterPosition) }
                binding.contributionEdit.setOnClickListener { presenter.openContributionInEditMode(holder.adapterPosition) }
            } else {
                binding.itemContributionId.visibility = View.INVISIBLE
            }
        }

        override fun getItemCount(): Int = contributionsDTO.contributions.size + 1

        inner class ViewHolder(val binding: ItemContributionBinding) : RecyclerView.ViewHolder(binding.root)
    }

}



