/**
 * This class is generated by jOOQ
 */
package trzye.zrzutka.jooq.model.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class Summary implements Serializable {

	private static final long serialVersionUID = 914485379;

	private Integer colorpricesumid;
	private Integer colorsummaryid;
	private Integer id;
	private Short   issorteddescending;
	private Short   precisecalculation;
	private Long    pricesum;
	private String  sortedcolumn;

	public Summary() {}

	public Summary(Summary value) {
		this.colorpricesumid = value.colorpricesumid;
		this.colorsummaryid = value.colorsummaryid;
		this.id = value.id;
		this.issorteddescending = value.issorteddescending;
		this.precisecalculation = value.precisecalculation;
		this.pricesum = value.pricesum;
		this.sortedcolumn = value.sortedcolumn;
	}

	public Summary(
		Integer colorpricesumid,
		Integer colorsummaryid,
		Integer id,
		Short   issorteddescending,
		Short   precisecalculation,
		Long    pricesum,
		String  sortedcolumn
	) {
		this.colorpricesumid = colorpricesumid;
		this.colorsummaryid = colorsummaryid;
		this.id = id;
		this.issorteddescending = issorteddescending;
		this.precisecalculation = precisecalculation;
		this.pricesum = pricesum;
		this.sortedcolumn = sortedcolumn;
	}

	public Integer getColorpricesumid() {
		return this.colorpricesumid;
	}

	public void setColorpricesumid(Integer colorpricesumid) {
		this.colorpricesumid = colorpricesumid;
	}

	public Integer getColorsummaryid() {
		return this.colorsummaryid;
	}

	public void setColorsummaryid(Integer colorsummaryid) {
		this.colorsummaryid = colorsummaryid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getIssorteddescending() {
		return this.issorteddescending;
	}

	public void setIssorteddescending(Short issorteddescending) {
		this.issorteddescending = issorteddescending;
	}

	public Short getPrecisecalculation() {
		return this.precisecalculation;
	}

	public void setPrecisecalculation(Short precisecalculation) {
		this.precisecalculation = precisecalculation;
	}

	public Long getPricesum() {
		return this.pricesum;
	}

	public void setPricesum(Long pricesum) {
		this.pricesum = pricesum;
	}

	public String getSortedcolumn() {
		return this.sortedcolumn;
	}

	public void setSortedcolumn(String sortedcolumn) {
		this.sortedcolumn = sortedcolumn;
	}
}
