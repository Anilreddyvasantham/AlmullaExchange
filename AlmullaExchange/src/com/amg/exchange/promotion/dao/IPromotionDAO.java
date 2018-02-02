package com.amg.exchange.promotion.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.model.ViewRemittanceDocument;
import com.amg.exchange.promotion.model.PromotionDetails;
import com.amg.exchange.promotion.model.PromotionMaster;
import com.amg.exchange.promotion.model.PromotionPrize;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;

public interface IPromotionDAO {
	
    public void savePromotionMaster(PromotionMaster promotionMaster,List<PromotionPrize> lstPromotionPrize);
    public void updatePromotionPrize(List<PromotionPrize> lstPromotionPrize,BigDecimal promotionMasterId,String userName);	
	public List<PromotionMaster> docNumbers();	
	public List<PromotionMaster> lstPromotionMaster(BigDecimal companyId, BigDecimal documentId,BigDecimal applicationYear,BigDecimal docNum);
	public List<PromotionPrize> lstPromotionPrize(BigDecimal exPromoHdSeq);	
	public void delete(BigDecimal promoPrizeSeq);
	
	public List<PopulateData> getPromotionDocumentNo(BigDecimal companyId,BigDecimal documentId,BigDecimal applicationYear);
	public List<PromotionMaster> getPromotionMasterDetails(BigDecimal companyId,BigDecimal documentId,BigDecimal applicationYear,BigDecimal documentNo);
	public List<PromotionPrize> getPromotionPrizeDetails(BigDecimal appicationCountryId,BigDecimal documentId,BigDecimal promotionMasterId);
	public List<PromotionDetails> getPromotionDetails(BigDecimal promotionMasterId);
	
	public void savePromotionDetails(List<PromotionDetails> lstPromotionDetails);
	public List<ViewRemiitanceInfo> verifyRemittanceDetails(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo,BigDecimal customerId);
	
	public List<ViewRemittanceDocument> verifyRemittanceDetailsWithRemitTransaction(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo ,BigDecimal customerId);
	
	public List<ViewRemiitanceInfo> verifyRemittanceDetailsBasedOnTransId(BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,BigDecimal transActionId);
	public void deletePromotionDetails(BigDecimal promotionDetailsId);
	public void savePromotionDeclaration(BigDecimal promotionDeclartionId,java.sql.Clob custSignature,String userName);
	public PromotionDetails getPromotionDetailsReport(BigDecimal promotionDetailsId);
	public List<PromotionDetails> getPromotionEnquiryDetails(BigDecimal promotionMasterId);
	public HashMap<String, List<PromotionPrize>> getPromotionPrizeDetailsForWinner(
			BigDecimal appicationCountryId, BigDecimal documentId,
			BigDecimal promotionMasterId);
	
	public List<PromotionDetails> promotionDetailsList(BigDecimal promotionPrizeSeq);
	
	public BigDecimal getPromoUsedAmount(BigDecimal promotionDetailsId);
	public List<ViewRemittanceDocument> verifyRemittanceBasedOnTransId(BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,BigDecimal transActionId);
	public List<PromotionPrize> getPrizeNoFromDate(BigDecimal promotionMasterId,Date fromDate);

}
