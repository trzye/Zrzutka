package trzye.zrzutka

import android.app.Application
import trzye.zrzutka.model.DATABASE_FILENAME
import trzye.zrzutka.model.DatabaseService
import trzye.zrzutka.model.ModelProvider

class ApplicationSingleton : Application() {

    override fun onCreate() {
        super.onCreate()
        deleteDatabase(DATABASE_FILENAME)
        ModelProvider.databaseService = DatabaseService(this)
    }

}