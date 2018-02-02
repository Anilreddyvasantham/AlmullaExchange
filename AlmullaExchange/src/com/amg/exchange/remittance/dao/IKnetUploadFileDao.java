package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.remittance.bean.KnetTranxFileUploadDatatable;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.CustomerDBCardDetailsView;
import com.amg.exchange.remittance.model.KnetDownload;
import com.amg.exchange.remittance.model.ViewKnetTrnxRelease;
import com.amg.exchange.util.AMGException;

public interface IKnetUploadFileDao {
	
	
public void saveKnetDownLoadFileDetails(KnetDownload knetDownload);
	
public List<KnetDownload> checkDupliateKnetTrnx(KnetTranxFileUploadDatatable knetTranxFileUploadDatatable);

public int checkCustomerReference(KnetTranxFileUploadDatatable knetTranxFileUploadDatatable);

public HashMap<String ,Object> callKnetTransferProcedureForUpdate(KnetTranxFileUploadDatatable knetTrnxDataTable) throws AMGException;

public HashMap<String ,Object> callKnetTransferProcedureForRefund(BigDecimal compId,BigDecimal docCode,BigDecimal docFyr,BigDecimal docNo,String pind,String userName) throws AMGException;

public List<CustomerDBCardDetailsView> customerBanksView(BigDecimal customerId, String cardNumber,KnetTranxFileUploadDatatable knetTrnxDataTable);

public List<CustomerDBCardDetailsView> duplicateCustomerBanksViewCheck(BigDecimal customerId, String cardNumber,String bankCode);

public List<ViewKnetTrnxRelease> getKnetTrnxOnHoldListfromView(BigDecimal customerId);


public String callKnetReleasePro(String trnxDate,BigDecimal collDocCode,BigDecimal collDoFyr,BigDecimal collDocNo,String user) throws AMGException;







	

}
