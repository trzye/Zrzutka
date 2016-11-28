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


    val summaryDao: Dao<Summary, Long> = getDao(Summary::class.java)
    val contributionDao: Dao<Contribution, Long> = getDao(Contribution::class.java)
    val friendDao: Dao<Friend, Long> = getDao(Friend::class.java)
    val contributorDao: Dao<Contributor, Long> = getDao(Contributor::class.java)
    val purchaseDao: Dao<Purchase, Long> = getDao(Purchase::class.java)
    val chargeDao: Dao<Charge, Long> = getDao(Charge::class.java)


    override fun getContribution(contributionId: Long?): Contribution =
            if (contributionId == null) Contribution("") else contributionDao.queryForId(contributionId).apply {
                summary.setBy(summaryDao.queryForId(summary.id))
                _contributors.forEach { contributor ->
                    contributor.friend.setBy(friendDao.queryForId(contributor.friend.id))
                }
                _purchases.forEach {
                    it._charges.forEach { charge ->
                        charge.charged = this.contributors.find { it._charges.find { it.id == charge.id } != null }
                    }
                }
            }

    override fun getAllContributions(): List<Contribution> {
        return contributionDao.queryForAll().apply {
            forEach { contribution ->
                contribution.summary.setBy(summaryDao.queryForId(contribution.summary.id))
                contribution._contributors.forEach { contributor ->
                    contributor.friend.setBy(friendDao.queryForId(contributor.friend.id))
                }
                contribution._purchases.forEach {
                    it._charges.forEach { charge ->
                        charge.charged = contribution.contributors.find { it._charges.find { it.id == charge.id } != null }
                    }
                }
            }
        }.sortedBy { -it.chargeUniqueNumberForSorting }
    }

    override fun removeContribution(contributionId: Long) {
        val contribution = contributionDao.queryForId(contributionId)
        if(contribution != null){
            contribution.purchases.forEach {
                it.charges.forEach { chargeDao.deleteById(it.id) }
                purchaseDao.deleteById(it.id)
            }
            contribution.contributors.forEach {
                contributorDao.deleteById(it.id)
            }
            summaryDao.deleteById(contribution.summary.id)
            contributionDao.deleteById(contribution.id)
        }
    }

    override fun save(contribution: Contribution): Long {
        if(contribution.id != null){
            removeContribution(contribution.id)
        }
        saveSummary(contribution.summary)
        saveContribution(contribution)
        contribution.contributors.forEach {
            saveFriend(it.friend)
            saveContributor(it)
        }
        contribution.purchases.forEach {
            savePurchase(it)
            it.charges.forEach { saveCharge(it) }
        }
//        contribution.purchases.forEach {
//            it.charges.forEach { saveCharge(it) }
//            savePurchase(it)
//        }
//        contribution.contributors.forEach {
//            saveContributor(it)
//            saveFriend(it.friend)
//        }
//        saveSummary(contribution.summary)
//        saveContribution(contribution)
        return contribution.id ?: throw IllegalArgumentException("Database saving problem. Can't extract presenterId after saving.")
    }

    private fun saveContribution(contribution: Contribution) {
        if(contribution.id == null){
            contributionDao.create(contribution)
        } else {
            contributionDao.createOrUpdate(contribution)
        }
    }

    private fun saveSummary(summary: Summary) {
        if(summary.id == null){
            summaryDao.create(summary)
        } else {
            summaryDao.createOrUpdate(summary)
        }
    }

    private fun saveFriend(friend: Friend) {
        if(friend.id == null){
            friendDao.create(friend)
        } else {
            friendDao.createOrUpdate(friend)
        }
    }

    private fun saveCharge(charge: Charge) {
        if(charge.id == null){
            chargeDao.create(charge)
        } else {
            chargeDao.createOrUpdate(charge)
        }
    }

    private fun savePurchase(purchase: Purchase) {
        if(purchase.id == null){
            purchaseDao.create(purchase)
        } else {
            purchaseDao.createOrUpdate(purchase)
        }
    }

    private fun saveContributor(contributor: Contributor) {
        if(contributor.id == null){
            contributorDao.create(contributor)
        } else {
            contributorDao.createOrUpdate(contributor)
        }
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