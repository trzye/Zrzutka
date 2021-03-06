/**
 * This class is generated by jOOQ
 */
package trzye.zrzutka.jooq.model.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import trzye.zrzutka.jooq.model.tables.Friend;
import trzye.zrzutka.jooq.model.tables.records.FriendRecord;


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
public class FriendDao extends DAOImpl<FriendRecord, trzye.zrzutka.jooq.model.tables.pojos.Friend, Integer> {

	/**
	 * Create a new FriendDao without any configuration
	 */
	public FriendDao() {
		super(Friend.FRIEND, trzye.zrzutka.jooq.model.tables.pojos.Friend.class);
	}

	/**
	 * Create a new FriendDao with an attached configuration
	 */
	public FriendDao(Configuration configuration) {
		super(Friend.FRIEND, trzye.zrzutka.jooq.model.tables.pojos.Friend.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(trzye.zrzutka.jooq.model.tables.pojos.Friend object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>colorId IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Friend> fetchByColorid(Integer... values) {
		return fetch(Friend.FRIEND.COLORID, values);
	}

	/**
	 * Fetch records that have <code>contactInformation IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Friend> fetchByContactinformation(String... values) {
		return fetch(Friend.FRIEND.CONTACTINFORMATION, values);
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Friend> fetchById(Integer... values) {
		return fetch(Friend.FRIEND.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public trzye.zrzutka.jooq.model.tables.pojos.Friend fetchOneById(Integer value) {
		return fetchOne(Friend.FRIEND.ID, value);
	}

	/**
	 * Fetch records that have <code>nickname IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Friend> fetchByNickname(String... values) {
		return fetch(Friend.FRIEND.NICKNAME, values);
	}

	/**
	 * Fetch records that have <code>paymentInformation IN (values)</code>
	 */
	public List<trzye.zrzutka.jooq.model.tables.pojos.Friend> fetchByPaymentinformation(String... values) {
		return fetch(Friend.FRIEND.PAYMENTINFORMATION, values);
	}
}
