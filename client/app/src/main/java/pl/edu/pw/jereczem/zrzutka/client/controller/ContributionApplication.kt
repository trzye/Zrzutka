package pl.edu.pw.jereczem.zrzutka.client.controller

import android.app.Application
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contribution

class ContributionApplication : Application() {
    var contribution : Contribution = Contribution("NULL")
}
