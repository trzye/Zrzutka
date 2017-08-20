/**
 * This class is generated by jOOQ
 */
package trzye.zrzutka.jooq.model.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import trzye.zrzutka.jooq.model.DefaultSchema;
import trzye.zrzutka.jooq.model.Keys;
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
public class Friend extends TableImpl<FriendRecord> {

	private static final long serialVersionUID = -2023776816;

	/**
	 * The reference instance of <code>friend</code>
	 */
	public static final Friend FRIEND = new Friend();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<FriendRecord> getRecordType() {
		return FriendRecord.class;
	}

	/**
	 * The column <code>friend.colorId</code>.
	 */
	public final TableField<FriendRecord, Integer> COLORID = createField("colorId", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>friend.contactInformation</code>.
	 */
	public final TableField<FriendRecord, String> CONTACTINFORMATION = createField("contactInformation", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * The column <code>friend.id</code>.
	 */
	public final TableField<FriendRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>friend.nickname</code>.
	 */
	public final TableField<FriendRecord, String> NICKNAME = createField("nickname", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * The column <code>friend.paymentInformation</code>.
	 */
	public final TableField<FriendRecord, String> PAYMENTINFORMATION = createField("paymentInformation", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * Create a <code>friend</code> table reference
	 */
	public Friend() {
		this("friend", null);
	}

	/**
	 * Create an aliased <code>friend</code> table reference
	 */
	public Friend(String alias) {
		this(alias, FRIEND);
	}

	private Friend(String alias, Table<FriendRecord> aliased) {
		this(alias, aliased, null);
	}

	private Friend(String alias, Table<FriendRecord> aliased, Field<?>[] parameters) {
		super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<FriendRecord> getPrimaryKey() {
		return Keys.PK_FRIEND;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<FriendRecord>> getKeys() {
		return Arrays.<UniqueKey<FriendRecord>>asList(Keys.PK_FRIEND);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Friend as(String alias) {
		return new Friend(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Friend rename(String name) {
		return new Friend(name, null);
	}
}
