package trzye.zrzutka.fatclient.summaryfragment

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject
import org.springframework.http.*
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.summaryfragment.SummaryFragmentContract.View
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.dto.web.ContributionDTO
import trzye.zrzutka.model.entity.summary.SortedColumn

class SummaryFragmentPresenter(private val databaseService: IDatabaseService) : SummaryFragmentContract.Presenter() {

    private lateinit var dataHolder: ContributionDataHolder

    override fun init(dataHolder: ContributionDataHolder) {
        this.dataHolder = dataHolder
    }

    override fun bindData(){
        view.bindData(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }

    override fun setEditMode() {
        dataHolder.isEditable = true
        view.setPreciseModeSwitchActive()
    }

    override fun setReadMode() {
        dataHolder.isEditable = false
        view.setPreciseModeSwitchInactive()
    }

    override fun updateView(view: View) {
        this.view = view
        view.changeDataSet(dataHolder.contribution)
        if(dataHolder.isEditable) setEditMode() else setReadMode()
    }

    override fun changePreciseMode() {
        val summary = dataHolder.contribution.summary
        summary.preciseCalculation = !summary.preciseCalculation
        view.changeDataSet(dataHolder.contribution)
        databaseService.save(dataHolder.contribution)
    }

    override fun generateSummaryUrl() {
        val contributionDTO = ContributionDTO(dataHolder.contribution)
        val task = Task()
        task.execute(contributionDTO)
        val result = task.get()
    }

    private class Task : AsyncTask<ContributionDTO, Void, Long>(){
        override fun doInBackground(vararg params: ContributionDTO): Long {
            try {
//                val restTemplate = RestTemplate().apply {
//                    requestFactory = SimpleClientHttpRequestFactory().apply {
//                        setConnectTimeout(6000) // 6 seconds
//                    }
//                }
                val uri = UriComponentsBuilder.fromHttpUrl("http://192.168.1.10").port(8080).path("/krs-rest-services").build().toUri()
                val request = Gson().toJson(params[0])
//
//                val headers = HttpHeaders().apply {
//                    contentType = MediaType.APPLICATION_JSON
//                }
//
//                val entity = HttpEntity<String>(request, headers)
//
//                Log.d("D/Zrzutka", request)
//
//                return restTemplate.exchange(uri, HttpMethod.POST, entity, Long::class.java).body

                val headers = HttpHeaders();
                headers.contentType = MediaType.APPLICATION_JSON

                Log.d("D/Zrzutka", request)

                val entity = HttpEntity<String>(request, headers)
                RestTemplate().put(uri, entity)
                return 0

            } catch (e: Exception){
                Log.d("D/Zrzutka", "$e\n${e.message}")
                return 0
            }
        }
    }

    override fun orderByWhoPaysHeader() {
        orderBy(SortedColumn.WHO_PAYS)
    }

    override fun orderByToWhomHeader() {
        orderBy(SortedColumn.TO_WHOM)
    }

    override fun orderByAmountHeader() {
        orderBy(SortedColumn.AMOUNT)
    }

    private fun orderBy(sortedSummary: SortedColumn) {
        if (dataHolder.contribution.summary.sortedColumn == sortedSummary) {
            dataHolder.contribution.summary.isSortedDescending = !dataHolder.contribution.summary.isSortedDescending
        } else {
            dataHolder.contribution.summary.sortedColumn = sortedSummary
        }
        view.changeDataSet(dataHolder.contribution)
        databaseService.save(dataHolder.contribution)
    }

}

