package trzye.zrzutka

import android.app.Application
import trzye.zrzutka.model.DatabaseService
import trzye.zrzutka.model.ModelProvider

class ApplicationSingleton : Application() {

    override fun onCreate() {
        super.onCreate()
        ModelProvider.databaseService = DatabaseService(this)
    }

}