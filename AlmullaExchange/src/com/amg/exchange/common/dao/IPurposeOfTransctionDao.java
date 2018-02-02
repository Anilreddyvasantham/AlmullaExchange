package com.amg.exchange.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;

public interface IPurposeOfTransctionDao {

	public List<String> toFetchPurposeOfCodeList(String query);

	public List<PurposeOfTransaction> toPurposeOfCodeAllValues(String purposeOfCode);

	public List<PurposeOfTransaction> toPurposeNamebyId(BigDecimal purposeId);

}
