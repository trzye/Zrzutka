package trzye.zrzutka.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import trzye.zrzutka.model.entity.purchase.Purchase
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.model.entity.summary.Summary

const val DATABASE_FILENAME = "Database.sqlite"

class DatabaseService(context: Context): OrmLiteSqliteOpenHelper(context, DATABASE_FILENAME, null, 1), IDatabaseService {

    val contributionDao: Dao<Contribution, Long> = getDao(Contribution::class.java)
    val summaryDao: Dao<Summary, Long> = getDao(Summary::class.java)


    override fun getContribution(contributionId: Long?): Contribution =
            if (contributionId == null) Contribution("") else contributionDao.queryForId(contributionId)

    override fun getAllContributions(): List<Contribution> {
        return contributionDao.queryForAll()
    }

    override fun removeContribution(contributionId: Long) {
        contributionDao.deleteById(contributionId) // TODO
    }

    override fun save(contribution: Contribution): Long {
        if(contribution.id == null){
            summaryDao.create(contribution.summary)
            contributionDao.create(contribution)
        } else {
            summaryDao.createOrUpdate(contribution.summary)
            contributionDao.createOrUpdate(contribution)
        }
        return contribution.id ?: throw IllegalArgumentException("Database saving problem. Can't extract presenterId after saving.")
    }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTable(connectionSource, Contribution::class.java)
        TableUtils.createTable(connectionSource, Summary::class.java)
        TableUtils.createTable(connectionSource, Friend::class.java)
        TableUtils.createTable(connectionSource, Purchase::class.java)
        TableUtils.createTable(connectionSource, Charge::class.java)
        TableUtils.createTable(connectionSource, Contributor::class.java)
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        throw UnsupportedOperationException("not implemented")
    }
}