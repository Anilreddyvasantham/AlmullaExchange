package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ForgotPasswordQuestionBean implements Serializable {

	private static final long serialVersionUID = -8409504387515033042L;
	private int position;
	private BigDecimal questionId;
	private String questiondesc;
	private String questionanswer;
	private String questionactualanswer;

	public ForgotPasswordQuestionBean() {
	}

	public ForgotPasswordQuestionBean(int position, BigDecimal questionId, String questiondesc, String questionanswer, String questionactualanswer) {
		this.position = position;
		this.questionId = questionId;
		this.questiondesc = questiondesc;
		this.questionanswer = questionanswer;
		this.questionactualanswer = questionactualanswer;
	}

	public String getQuestionactualanswer() {
		return questionactualanswer;
	}

	public void setQuestionactualanswer(String questionactualanswer) {
		this.questionactualanswer = questionactualanswer;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public BigDecimal getQuestionId() {
		return questionId;
	}

	public void setQuestionId(BigDecimal questionId) {
		this.questionId = questionId;
	}

	public String getQuestiondesc() {
		return questiondesc;
	}

	public void setQuestiondesc(String questiondesc) {
		this.questiondesc = questiondesc;
	}

	public String getQuestionanswer() {
		return questionanswer;
	}

	public void setQuestionanswer(String questionanswer) {
		this.questionanswer = questionanswer;
	}

	@Override
	public String toString() {
		return "ForgotPasswordQuestionBean [position=" + position + ", questionId=" + questionId + ", questiondesc=" + questiondesc + ", questionanswer=" + questionanswer + ", questionactualanswer=" + questionactualanswer + "]";
	}

}
