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
import trzye.zrzutka.jooq.model.tables.records.PurchaseRecord;


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
public class Purchase extends TableImpl<PurchaseRecord> {

	private static final long serialVersionUID = 295609208;

	/**
	 * The reference instance of <code>purchase</code>
	 */
	public static final Purchase PURCHASE = new Purchase();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<PurchaseRecord> getRecordType() {
		return PurchaseRecord.class;
	}

	/**
	 * The column <code>purchase.colorId</code>.
	 */
	public final TableField<PurchaseRecord, Integer> COLORID = createField("colorId", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>purchase.contribution_id</code>.
	 */
	public final TableField<PurchaseRecord, Long> CONTRIBUTION_ID = createField("contribution_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>purchase.id</code>.
	 */
	public final TableField<PurchaseRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>purchase.name</code>.
	 */
	public final TableField<PurchaseRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * The column <code>purchase.price</code>.
	 */
	public final TableField<PurchaseRecord, Long> PRICE = createField("price", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>purchase</code> table reference
	 */
	public Purchase() {
		this("purchase", null);
	}

	/**
	 * Create an aliased <code>purchase</code> table reference
	 */
	public Purchase(String alias) {
		this(alias, PURCHASE);
	}

	private Purchase(String alias, Table<PurchaseRecord> aliased) {
		this(alias, aliased, null);
	}

	private Purchase(String alias, Table<PurchaseRecord> aliased, Field<?>[] parameters) {
		super(alias, DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<PurchaseRecord> getPrimaryKey() {
		return Keys.PK_PURCHASE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<PurchaseRecord>> getKeys() {
		return Arrays.<UniqueKey<PurchaseRecord>>asList(Keys.PK_PURCHASE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Purchase as(String alias) {
		return new Purchase(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Purchase rename(String name) {
		return new Purchase(name, null);
	}
}
