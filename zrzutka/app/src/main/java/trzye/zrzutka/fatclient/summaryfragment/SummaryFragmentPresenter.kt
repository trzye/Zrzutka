package trzye.zrzutka.fatclient.summaryfragment

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import trzye.zrzutka.fatclient.contributionfragment.ContributionDataHolder
import trzye.zrzutka.fatclient.summaryfragment.SummaryFragmentContract.View
import trzye.zrzutka.model.IDatabaseService
import trzye.zrzutka.model.ModelProvider
import trzye.zrzutka.model.dto.web.ContributionDTO
import trzye.zrzutka.model.entity.summary.SortedColumn
import java.nio.charset.Charset


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
    }

    inner class Task : AsyncTask<ContributionDTO, Void, Long>(){

        override fun doInBackground(vararg params: ContributionDTO): Long {
            try {
                val uri = UriComponentsBuilder.fromHttpUrl("http://${ModelProvider.IP}").port(8080).path("/krs-rest-services").build().toUri()
                val request = Gson().toJson(params[0])

                val headers = HttpHeaders();
                headers.contentType = MediaType.APPLICATION_JSON

                Log.d("D/Zrzutka", request)

                val entity = HttpEntity<String>(request, headers)
                val response = RestTemplate().setTimeout(2000).apply {
                    messageConverters.add(0, StringHttpMessageConverter(Charset.forName("UTF-8")))
                }.exchange(uri, HttpMethod.POST, entity, String::class.java)
                return response.body.toLong()
            } catch (e: RestClientException){
                Log.d("D/Zrzutka", "$e\n${e.message}")
                return -1L
            }
        }

        private fun RestTemplate.setTimeout(timeout: Int) : RestTemplate {
            this.requestFactory = SimpleClientHttpRequestFactory()
            val rf = this.requestFactory as SimpleClientHttpRequestFactory
            rf.setReadTimeout(timeout)
            rf.setConnectTimeout(timeout)
            return this
        }

        override fun onPreExecute() {
            super.onPreExecute()
            view.showLoadingView()
        }

        override fun onPostExecute(result: Long) {
            super.onPostExecute(result)
            view.dismissLoadingView()
            if(result == -1L){
                view.showConnectionErrorMessage()
            } else {
                view.showShareResultDialog(result)
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

