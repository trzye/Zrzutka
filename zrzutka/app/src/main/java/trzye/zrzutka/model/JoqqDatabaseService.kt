package trzye.zrzutka.model

import android.content.Context
import android.util.Log
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import org.jooq.*
import org.jooq.impl.DAOImpl
import org.jooq.impl.DSL
import org.jooq.impl.DefaultDSLContext
import trzye.zrzutka.jooq.model.Tables
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
        create.deleteFrom(CONTRIBUTION).execute()
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
            Contributor(
                    it,
                    Friend(friendDao.findById(it.friendId.toInt())),
                    null,
                    charges
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
            chargesDao.fetchByPurchaseId(purchase.jooqPurchase.id.toLong()).map {
                Charge(it, null, purchase)
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
            it.jooqContributor.id = insert(it.jooqContributor, it.jooqContributor.id, contributorDao).id
        }
        contribution.purchases.forEach {
            it.jooqPurchase.id = insert(it.jooqPurchase, it.jooqPurchase.id, pruchaseDao).id
        }
        contribution.contributors.forEach {
            it.charges.forEach {
                it.jooqCharge.id = insert(it.jooqCharge, it.jooqCharge.id, chargesDao).id
            }
        }
        contribution.purchases.forEach {
            it.charges.forEach {
                it.jooqCharge.id = insert(it.jooqCharge, it.jooqCharge.id, chargesDao).id
            }
        }
        contribution.summary.jooqSummary.id =
                insert(contribution.summary.jooqSummary, contribution.summary.jooqSummary.id, summaryDao).id
        contribution.jooqContribution.summaryId =  contribution.summary.jooqSummary.id.toLong()
        contribution.jooqContribution.id =
                insert(contribution.jooqContribution, contribution.jooqContribution.id, contributionDao).id
        return contribution.jooqContribution.id.toLong()
    }

    override fun getAllFriends(): List<Friend> = friendDao.findAll().map { Friend(it) }

    override fun save(friend: Friend) {
        insert(friend.jooqFriend, friend.id?.toInt(), friendDao)
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
        if(friend.id != null)
            if(friendDao.existsById(friend.id.toInt()))
                friendDao.deleteById(friend.id.toInt())
    }

}
//
//private fun <R: UpdatableRecord<R>, P, T> DAOImpl<R, P, T>.insertAndFetch(record: P): R {
//    val create =  DefaultDSLContext(configuration())
//    val newRecord = create.newRecord(table, record)
//    newRecord.st
//}
