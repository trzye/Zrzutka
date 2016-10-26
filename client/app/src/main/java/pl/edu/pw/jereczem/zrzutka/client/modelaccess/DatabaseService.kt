package pl.edu.pw.jereczem.zrzutka.client.modelaccess

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Charge
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contribution
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Contributor
import pl.edu.pw.jereczem.zrzutka.client.model.contribution.Purchase
import pl.edu.pw.jereczem.zrzutka.client.model.friend.Friend

const val DATABASE_FILENAME = "Database.sqlite"

class DatabaseService(context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_FILENAME, null, 1) {

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTable(connectionSource, Contribution::class.java)
        TableUtils.createTable(connectionSource, Friend::class.java)
        TableUtils.createTable(connectionSource, Purchase::class.java)
        TableUtils.createTable(connectionSource, Charge::class.java)
        TableUtils.createTable(connectionSource, Contributor::class.java)
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        throw UnsupportedOperationException("not implemented")
    }

    val contributionDao = getDao(Contribution::class.java)
    val contributorDao = getDao(Contributor::class.java)
    val friendDao = getDao(Friend::class.java)
    val purchaseDao = getDao(Purchase::class.java)
    val chargeDao = getDao(Charge::class.java)

}