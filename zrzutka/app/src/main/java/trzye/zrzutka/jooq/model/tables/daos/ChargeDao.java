/**
 * This class is generated by jOOQ
 */
package trzye.zrzutka.jooq.model.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import trzye.zrzutka.jooq.model.tables.Charge;
import trzye.zrzutka.jooq.model.tables.records.ChargeRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChargeDao extends DAOImpl<ChargeRecord, trzye.zrzutka.jooq.model.tables.pojos.Charge, Integer> {

	/**
	 * Create a new ChargeDao without any configuration
	 */
	public ChargeDao() {
		super(Charge.CHARGE, trzye.zrzutka.jooq.model.tables.pojos.Charge.class);
	}

	/**
	 * Create a new ChargeDao with an attached configuration
	 */
	public ChargeDao(Configuration configuration) {
		super(Charge.CHARGE, trzye.zrzutka.jooq.model.tables.pojos.Charge.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(trzye.zrzutka.jooq.model.tables.pojos.Charge object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>amountPaid IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Charge> fetchByAmountpaid(Long... values) {
		return fetch(Charge.CHARGE.AMOUNTPAID, values);
	}

	/**
	 * Fetch records that have <code>amountToPay IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Charge> fetchByAmounttopay(Long... values) {
		return fetch(Charge.CHARGE.AMOUNTTOPAY, values);
	}

	/**
	 * Fetch records that have <code>chargeUniqueNumberForSorting IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Charge> fetchByChargeuniquenumberforsorting(Long... values) {
		return fetch(Charge.CHARGE.CHARGEUNIQUENUMBERFORSORTING, values);
	}

	/**
	 * Fetch records that have <code>charged_id IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Charge> fetchByChargedId(Long... values) {
		return fetch(Charge.CHARGE.CHARGED_ID, values);
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Charge> fetchById(Integer... values) {
		return fetch(Charge.CHARGE.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public trzye.zrzutka.jooq.model.tables.pojos.Charge fetchOneById(Integer value) {
		return fetchOne(Charge.CHARGE.ID, value);
	}

	/**
	 * Fetch records that have <code>purchase_id IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Charge> fetchByPurchaseId(Long... values) {
		return fetch(Charge.CHARGE.PURCHASE_ID, values);
	}
}
