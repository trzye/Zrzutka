package trzye.zrzutka.model

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import org.jooq.*
import org.jooq.impl.DAOImpl
import org.jooq.impl.DSL
import trzye.zrzutka.jooq.model.Tables.*
import trzye.zrzutka.jooq.model.tables.daos.*
import trzye.zrzutka.model.entity.charge.Charge
import trzye.zrzutka.model.entity.contribution.Contribution
import trzye.zrzutka.model.entity.contributor.Contributor
import trzye.zrzutka.model.entity.friend.Friend
import trzye.zrzutka.model.entity.purchase.Purchase
import trzye.zrzutka.model.entity.summary.Summary
import java.sql.Connection
import java.sql.DriverManager

class JoqqDatabaseService(applicationContext: Context) : IDatabaseService {

    val connection = createConnection(applicationContext)
    val create = DSL.using(connection, SQLDialect.SQLITE)
    val friendDao = FriendDao(create.configuration())
    val contributionDao = ContributionDao(create.configuration())
    val summaryDao = SummaryDao(create.configuration())
    val contributorDao = ContributorDao(create.configuration())
    val chargesDao = ChargeDao(create.configuration())
    val pruchaseDao = PurchaseDao(create.configuration())

    init {
//        create.deleteFrom(CONTRIBUTION).execute()
//        create.deleteFrom(SUMMARY).execute()
//        create.deleteFrom(CHARGE).execute()
//        create.deleteFrom(PURCHASE).execute()
//        create.deleteFrom(CONTRIBUTOR).execute()
//        create.deleteFrom(FRIEND).execute()
    }

    private fun createConnection(applicationContext: Context): Connection? {
        Class.forName("org.sqldroid.SQLDroidDriver").newInstance()
        return DriverManager
                .getConnection("jdbc:sqlite:" + SQLiteAssetHelper(applicationContext, "node.db", null, 1)
                        .writableDatabase.path)
    }

    override fun getContribution(contributionId: Long?): Contribution {
        if(contributionId != null) {
            val contributionJooQ = contributionDao.findById(contributionId.toInt())
            if(contributionJooQ != null){
                val purchases = getPurchases(contributionId)
                val charges = getCharges(purchases)
                val contributors = getContributors(contributionId, charges)
                return Contribution(
                        contributionJooQ,
                        Summary(summaryDao.findById(contributionJooQ.summaryId.toInt())),
                        contributors,
                        purchases
                ).also {
                    contribution ->
                        purchases.forEach { it.contribution = contribution }
                        contributors.forEach { it.contribution = contribution }
                }
            }
        }
        return Contribution("")
    }

    private fun getContributors(contributionId: Long?, charges: MutableList<Charge>): MutableList<Contributor> {
        return contributorDao.fetchByContributionId(contributionId).map {
            cont ->
            Contributor(
                    cont,
                    Friend(friendDao.findById(cont.friendId.toInt())),
                    null,
                    charges.filter {
                        it.databasePojo().chargedId.toInt() == cont.id
                    }.toMutableList()
            ).also {
                contributor ->
                contributor._charges.forEach { it.charged = contributor }
            }
        }.toMutableList().also {
            it.forEach { contributor -> contributor._charges.forEach { it.charged = contributor } }
        }
    }

    private fun getCharges(purchases: MutableList<Purchase>): MutableList<Charge> {
        return purchases.flatMap {
            purchase ->
            chargesDao.fetchByPurchaseId(purchase.databasePojo().id.toLong()).map {
                Charge(it, null, purchase).also {
                    purchase._charges.add(it)
                }
            }
        }.toMutableList()
    }

    private fun getPurchases(contributionId: Long?): MutableList<Purchase> {
        return pruchaseDao.fetchByContributionId(contributionId).map {
            Purchase(it, null, mutableListOf())
        }.toMutableList()
    }

    override fun removeContribution(contributionId: Long) {
        contributionDao.findAll().forEach {
            contributionDao.deleteById(it.id)
            summaryDao.deleteById(it.summaryId.toInt())
        }
        pruchaseDao.fetchByContributionId(contributionId).forEach {
            chargesDao.fetchByPurchaseId(it.id.toLong()).forEach {
                chargesDao.deleteById(it.id)
            }
        }
        contributorDao.fetchByContributionId(contributionId).forEach {
            chargesDao.fetchByPurchaseId(it.id.toLong()).forEach {
                chargesDao.deleteById(it.id)
            }
        }
        pruchaseDao.fetchByContributionId(contributionId).forEach {
            pruchaseDao.deleteById(it.id)
        }
        contributorDao.fetchByContributionId(contributionId).forEach {
            contributorDao.deleteById(it.id)
        }
    }

    override fun getAllContributions(): List<Contribution>
            = contributionDao.findAll().map { getContribution(it.id.toLong()) }

    override fun save(contribution: Contribution): Long {
        contribution.contributors.forEach {
            it.setId(insert(it.databasePojo(), it.databasePojo().id, contributorDao).id)
        }
        contribution.purchases.forEach {
            it.setId(insert(it.databasePojo(), it.databasePojo().id, pruchaseDao).id)
        }
        contribution.contributors.forEach {
            contributor -> contributor.charges.forEach {
                it.charged = contributor
                it.setId(insert(it.databasePojo(), it.databasePojo().id, chargesDao).id)
            }
        }
        contribution.purchases.forEach {
            purchase -> purchase.charges.forEach {
                it.purchase = purchase
                it.setId(insert(it.databasePojo(), it.databasePojo().id, chargesDao).id)
            }
        }
        contribution.summary
                .setId(insert(contribution.summary.databasePojo(), contribution.summary.databasePojo().id, summaryDao).id)
        contribution.setId(insert(contribution.databasePojo(), contribution.databasePojo().id, contributionDao).id)
        return contribution.databasePojo().id.toLong()
    }

    override fun getAllFriends(): List<Friend> = friendDao.findAll().map { Friend(it) }

    override fun save(friend: Friend) {
        insert(friend.databasePojo(), friend.databasePojo().id, friendDao)
    }

    private fun <TR : UpdatableRecord<TR>, R, I>insert(record: R, id: I?, dao: DAOImpl<TR, R, I>): R {
        if (id != null) {
            dao.update(record)
            return record
        } else {
            dao.insert(record)
            return dao.findAll().last()
        }
    }

    override fun removeFriend(friend: Friend) {
        if(friend.databasePojo().id != null)
            if(friendDao.existsById(friend.databasePojo().id))
                friendDao.deleteById(friend.databasePojo().id)
    }

}
//
//private fun <R: UpdatableRecord<R>, P, T> DAOImpl<R, P, T>.insertAndFetch(record: P): R {
//    val create =  DefaultDSLContext(configuration())
//    val newRecord = create.newRecord(table, record)
//    newRecord.st
//}
