package com.amg.exchange.reprint.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.reprint.dao.IReprintDao;
import com.amg.exchange.reprint.service.IReprintService;

@Service("reprintService")
public class ReprintServiceImpl implements IReprintService {
	IReprintDao reprintDao;

	
	
	
	
	public IReprintDao getReprintDao() {
		return reprintDao;
	}

	@Autowired
	public void setReprintDao(IReprintDao reprintDao) {
		this.reprintDao = reprintDao;
	}


	@Override
	@Transactional
	public List<RemittanceApplicationView> getRecordsRemittanceReceipt(BigDecimal documentNumber, BigDecimal documentYear,BigDecimal applicationCountryId) {
		
		return getReprintDao().getRecordsRemittanceReceipt(documentNumber,documentYear,applicationCountryId);
	}

	@Override
	@Transactional
	public List<RemittanceApplicationView> getRemittancefromCollectionNumber(BigDecimal transactionNo, BigDecimal rEMITTANCE_DOCUMENT_CODE, BigDecimal documentYear,BigDecimal applicationCountryId) {
		 return getReprintDao().getRemittancefromCollectionNumber(transactionNo,rEMITTANCE_DOCUMENT_CODE,documentYear,applicationCountryId);
	}

}
