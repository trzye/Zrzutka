package trzye.zrzutka.fatclient.launcheractivity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import trzye.zrzutka.fatclient.mainactivity.MainActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        MainActivity(this).startAsContributionsMainActivity(true)
        this.finish()
    }
}