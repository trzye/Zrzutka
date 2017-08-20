/**
 * This class is generated by jOOQ
 */
package trzye.zrzutka.jooq.model.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import trzye.zrzutka.jooq.model.tables.Contributor;


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
public class ContributorRecord extends UpdatableRecordImpl<ContributorRecord> implements Record3<Long, Long, Integer> {

	private static final long serialVersionUID = -2079704325;

	/**
	 * Setter for <code>contributor.contribution_id</code>.
	 */
	public void setContributionId(Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>contributor.contribution_id</code>.
	 */
	public Long getContributionId() {
		return (Long) getValue(0);
	}

	/**
	 * Setter for <code>contributor.friend_id</code>.
	 */
	public void setFriendId(Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>contributor.friend_id</code>.
	 */
	public Long getFriendId() {
		return (Long) getValue(1);
	}

	/**
	 * Setter for <code>contributor.id</code>.
	 */
	public void setId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>contributor.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Long, Long, Integer> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Long, Long, Integer> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return Contributor.CONTRIBUTOR.CONTRIBUTION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return Contributor.CONTRIBUTOR.FRIEND_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Contributor.CONTRIBUTOR.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getContributionId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value2() {
		return getFriendId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContributorRecord value1(Long value) {
		setContributionId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContributorRecord value2(Long value) {
		setFriendId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContributorRecord value3(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContributorRecord values(Long value1, Long value2, Integer value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ContributorRecord
	 */
	public ContributorRecord() {
		super(Contributor.CONTRIBUTOR);
	}

	/**
	 * Create a detached, initialised ContributorRecord
	 */
	public ContributorRecord(Long contributionId, Long friendId, Integer id) {
		super(Contributor.CONTRIBUTOR);

		setValue(0, contributionId);
		setValue(1, friendId);
		setValue(2, id);
	}
}
