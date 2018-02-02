package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.bean.KnetTranxFileUploadDatatable;
import com.amg.exchange.remittance.dao.IKnetUploadFileDao;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.CustomerDBCardDetailsView;
import com.amg.exchange.remittance.model.KnetDownload;
import com.amg.exchange.remittance.model.ViewKnetTrnxRelease;
import com.amg.exchange.remittance.service.IKnetUploadFileService;
import com.amg.exchange.util.AMGException;

@Service
public class KnetUploadFileServiceImpl implements IKnetUploadFileService{

	@Autowired 
	IKnetUploadFileDao  knetUploadFileDao;
	
	
	@Override
	@Transactional
	public List<KnetDownload> checkDupliateKnetTrnx(KnetTranxFileUploadDatatable knetTranxFileUploadDatatable) {
		// TODO Auto-generated method stub
		return knetUploadFileDao.checkDupliateKnetTrnx(knetTranxFileUploadDatatable);
	}


	@Override
	@Transactional
	public int checkCustomerReference(KnetTranxFileUploadDatatable knetTranxFileUploadDatatable) {
		return knetUploadFileDao.checkCustomerReference(knetTranxFileUploadDatatable);
	}


	@Override
	@Transactional
	public void saveKnetDownLoadFileDetails(KnetDownload knetDownload) {
		knetUploadFileDao.saveKnetDownLoadFileDetails(knetDownload);
		
	}


	@Override
	@Transactional
	public HashMap<String, Object> callKnetTransferProcedureForUpdate(KnetTranxFileUploadDatatable knetTrnxDataTable) throws AMGException{
		return knetUploadFileDao.callKnetTransferProcedureForUpdate(knetTrnxDataTable);
	}


	@Override
	@Transactional
	public HashMap<String, Object> callKnetTransferProcedureForRefund(BigDecimal compId,BigDecimal docCode,BigDecimal docFyr,BigDecimal docNo,String pind,String userName) throws AMGException {
		return knetUploadFileDao.callKnetTransferProcedureForRefund(compId,docCode,docFyr,docNo,pind,userName);
	}


	@Override
	@Transactional
	public List<CustomerDBCardDetailsView> customerBanksView(BigDecimal customerId, String cardNumber,KnetTranxFileUploadDatatable knetTrnxDataTable) {
		// TODO Auto-generated method stub
		return knetUploadFileDao.customerBanksView(customerId, cardNumber,knetTrnxDataTable);
	}


	@Override
	@Transactional
	public List<ViewKnetTrnxRelease> getKnetTrnxOnHoldListfromView(BigDecimal customerId) {
		// TODO Auto-generated method stub
		return knetUploadFileDao.getKnetTrnxOnHoldListfromView(customerId);
	}


	@Override
	@Transactional
	public List<CustomerDBCardDetailsView> duplicateCustomerBanksViewCheck(BigDecimal customerId, String cardNumber, String bankCode) {
		return knetUploadFileDao.duplicateCustomerBanksViewCheck(customerId, cardNumber, bankCode);
	}


	@Override
	@Transactional
	public String callKnetReleasePro(String trnxDate, BigDecimal collDocCode, BigDecimal collDoFyr, BigDecimal collDocNo, String user)
			throws AMGException {
		return knetUploadFileDao.callKnetReleasePro(trnxDate, collDocCode, collDoFyr, collDocNo, user);
		
	}
	
	

}
