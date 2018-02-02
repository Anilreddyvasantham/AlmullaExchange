/**
 * 
 */
package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Subramaniam
 *
 */
@Entity
@Table(name="EX_AML_LIMIT")
public class AmlLimit implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private BigDecimal amlLimitId;
	private String rangeFromOne;
	private String rangeToOne;
	private String rangeFromTwo;
	private String rangeToTwo;
	private String rangeFromThree;
	private String rangeToThree;
	private String rangeFromFour;
	private String rangeToFour;
	private String amlRangeOneDescription;
	private String amlRangeTwoDescription;
	private String amlRangeThreeDescription;
	private String amlRangeFourDescription;
	

	
	public AmlLimit() {
	}
	public AmlLimit(BigDecimal amlLimitId, String rangeFromOne,
			String rangeToOne, String rangeFromTwo, String rangeToTwo,
			String rangeFromThree, String rangeToThree, String rangeFromFour,
			String rangeToFour, String amlRangeOneDescription,
			String amlRangeTwoDescription, String amlRangeThreeDescription,
			String amlRangeFourDescription) {
		this.amlLimitId = amlLimitId;
		this.rangeFromOne = rangeFromOne;
		this.rangeToOne = rangeToOne;
		this.rangeFromTwo = rangeFromTwo;
		this.rangeToTwo = rangeToTwo;
		this.rangeFromThree = rangeFromThree;
		this.rangeToThree = rangeToThree;
		this.rangeFromFour = rangeFromFour;
		this.rangeToFour = rangeToFour;
		this.amlRangeOneDescription = amlRangeOneDescription;
		this.amlRangeTwoDescription = amlRangeTwoDescription;
		this.amlRangeThreeDescription = amlRangeThreeDescription;
		this.amlRangeFourDescription = amlRangeFourDescription;
	}
	@Id
	@Column(name="AML_LIMIT_ID")
	public BigDecimal getAmlLimitId() {
		return amlLimitId;
	}
	public void setAmlLimitId(BigDecimal amlLimitId) {
		this.amlLimitId = amlLimitId;
	}
	@Column(name="RANGE1_FROM")
	public String getRangeFromOne() {
		return rangeFromOne;
	}
	public void setRangeFromOne(String rangeFromOne) {
		this.rangeFromOne = rangeFromOne;
	}
	@Column(name="RANGE1_TO")
	public String getRangeToOne() {
		return rangeToOne;
	}
	public void setRangeToOne(String rangeToOne) {
		this.rangeToOne = rangeToOne;
	}
	@Column(name="RANGE2_FROM")
	public String getRangeFromTwo() {
		return rangeFromTwo;
	}
	public void setRangeFromTwo(String rangeFromTwo) {
		this.rangeFromTwo = rangeFromTwo;
	}
	@Column(name="RANGE2_TO")
	public String getRangeToTwo() {
		return rangeToTwo;
	}
	public void setRangeToTwo(String rangeToTwo) {
		this.rangeToTwo = rangeToTwo;
	}
	@Column(name="RANGE3_FROM")
	public String getRangeFromThree() {
		return rangeFromThree;
	}
	public void setRangeFromThree(String rangeFromThree) {
		this.rangeFromThree = rangeFromThree;
	}
	@Column(name="RANGE3_TO")
	public String getRangeToThree() {
		return rangeToThree;
	}
	public void setRangeToThree(String rangeToThree) {
		this.rangeToThree = rangeToThree;
	}
	@Column(name="RANGE4_FROM")
	public String getRangeFromFour() {
		return rangeFromFour;
	}
	public void setRangeFromFour(String rangeFromFour) {
		this.rangeFromFour = rangeFromFour;
	}
	@Column(name="RANGE4_TO")
	public String getRangeToFour() {
		return rangeToFour;
	}
	public void setRangeToFour(String rangeToFour) {
		this.rangeToFour = rangeToFour;
	}
	@Column(name="RANGE1_DESCRIPTION")
	public String getAmlRangeOneDescription() {
		return amlRangeOneDescription;
	}
	public void setAmlRangeOneDescription(String amlRangeOneDescription) {
		this.amlRangeOneDescription = amlRangeOneDescription;
	}
	@Column(name="RANGE2_DESCRIPTION")
	public String getAmlRangeTwoDescription() {
		return amlRangeTwoDescription;
	}
	public void setAmlRangeTwoDescription(String amlRangeTwoDescription) {
		this.amlRangeTwoDescription = amlRangeTwoDescription;
	}
	@Column(name="RANGE3_DESCRIPTION")
	public String getAmlRangeThreeDescription() {
		return amlRangeThreeDescription;
	}
	public void setAmlRangeThreeDescription(String amlRangeThreeDescription) {
		this.amlRangeThreeDescription = amlRangeThreeDescription;
	}
	@Column(name="RANGE4_DESCRIPTION")
	public String getAmlRangeFourDescription() {
		return amlRangeFourDescription;
	}
	public void setAmlRangeFourDescription(String amlRangeFourDescription) {
		this.amlRangeFourDescription = amlRangeFourDescription;
	}
	
/*AML_LIMIT_ID
RANGE1_DESCRIPTION
RANGE1_FROM
RANGE1_TO
RANGE2_DESCRIPTION
RANGE2_FROM
RANGE2_TO
RANGE3_DESCRIPTION
RANGE3_FROM
RANGE3_TO
RANGE4_DESCRIPTION
RANGE4_FROM
RANGE4_TO*/
	
}
