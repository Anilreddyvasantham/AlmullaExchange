package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IPurposeOfTransctionDao;
import com.amg.exchange.common.service.IPurposeOfTransctionService;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
@Service("purposeOfTransctionService")
@Transactional
public class PurposeOfTransctionServiceImpl implements IPurposeOfTransctionService {

	@Autowired 
	IPurposeOfTransctionDao purposeOfTransctionDao;

	@Override
	public List<String> toFetchPurposeOfCodeList(String query) {
		return purposeOfTransctionDao.toFetchPurposeOfCodeList(query);
	}

	@Override
	public List<PurposeOfTransaction> toPurposeOfCodeAllValues(String purposeOfCode) {
		return purposeOfTransctionDao.toPurposeOfCodeAllValues(purposeOfCode);
	}

	@Override
	public List<PurposeOfTransaction> toPurposeNamebyId(BigDecimal purposeId) {
		return purposeOfTransctionDao.toPurposeNamebyId(purposeId);
	}
}
