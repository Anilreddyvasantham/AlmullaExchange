package com.amg.exchange.promotion.serviceimpl;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.model.ViewRemittanceDocument;
import com.amg.exchange.promotion.dao.IPromotionDAO;
import com.amg.exchange.promotion.model.PromotionDetails;
import com.amg.exchange.promotion.model.PromotionMaster;
import com.amg.exchange.promotion.model.PromotionPrize;
import com.amg.exchange.promotion.service.IPromotionService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;

@Service("promotionServiceImpl")
public class PromotionServiceImpl implements IPromotionService {

	@Autowired
	IPromotionDAO iPromotionDAO;
	
	@Override
	@Transactional
	public void savePromotionMaster(PromotionMaster promotionMaster,List<PromotionPrize> lstPromotionPrize) {
		iPromotionDAO.savePromotionMaster(promotionMaster, lstPromotionPrize);		
	}
	
	@Override
	@Transactional
	public void updatePromotionPrize(List<PromotionPrize> lstPromotionPrize,BigDecimal promotionMasterId,String userName) {
		iPromotionDAO.updatePromotionPrize(lstPromotionPrize,promotionMasterId,userName);		
	}
	
	@Override
	@Transactional
	public List<PromotionMaster> docNumbers() {
		return iPromotionDAO.docNumbers();		
	}
	
	@Override
	@Transactional
	public List<PromotionMaster> lstPromotionMaster(BigDecimal companyId, BigDecimal documentId,BigDecimal applicationYear,BigDecimal docNum) {
		return iPromotionDAO.lstPromotionMaster(companyId,documentId,applicationYear,docNum);		
	}
	
	@Override
	@Transactional
	public List<PromotionPrize> lstPromotionPrize(BigDecimal exPromoHdSeq) {
		return iPromotionDAO.lstPromotionPrize(exPromoHdSeq);		
	}
	
	@Override
	@Transactional
	public void delete(BigDecimal promoPrizeSeq) {
		iPromotionDAO.delete(promoPrizeSeq);		
	}
	
	@Override
	@Transactional
	public List<PopulateData> getPromotionDocumentNo(BigDecimal companyId,
			BigDecimal documentId, BigDecimal applicationYear) {
		
		return iPromotionDAO.getPromotionDocumentNo(companyId, documentId, applicationYear);
	}

	@Override
	@Transactional
	public List<PromotionMaster> getPromotionMasterDetails(
			BigDecimal companyId, BigDecimal documentId,
			BigDecimal applicationYear, BigDecimal documentNo) {
		
		return iPromotionDAO.getPromotionMasterDetails(companyId, documentId, applicationYear, documentNo);
	}

	@Override
	@Transactional
	public List<PromotionPrize> getPromotionPrizeDetails(
			BigDecimal appicationCountryId, BigDecimal documentId,
			BigDecimal promotionMasterId) {
		
		return iPromotionDAO.getPromotionPrizeDetails(appicationCountryId, documentId, promotionMasterId);
	}

	@Override
	@Transactional
	public List<PromotionDetails> getPromotionDetails(
			BigDecimal promotionMasterId) {
		return iPromotionDAO.getPromotionDetails(promotionMasterId);
	}

	@Override
	@Transactional
	public void savePromotionDetails(List<PromotionDetails> lstPromotionDetails) {
		iPromotionDAO.savePromotionDetails(lstPromotionDetails);
	}

	@Override
	@Transactional
	public List<ViewRemiitanceInfo> verifyRemittanceDetails(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal documentFinanceYear,
			BigDecimal documentNo,BigDecimal customerId) {
		
		return iPromotionDAO.verifyRemittanceDetails(appCountryId, companyId, documentCode, documentFinanceYear, documentNo,customerId);
	}
	
	@Override
	@Transactional
	public List<ViewRemittanceDocument> verifyRemittanceDetailsWithRemitTransaction(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo ,BigDecimal customerId) {
		
		return iPromotionDAO.verifyRemittanceDetailsWithRemitTransaction(appCountryId, companyId, documentCode, documentFinanceYear, documentNo,customerId);
	}

	@Override
	@Transactional
	public List<ViewRemiitanceInfo> verifyRemittanceDetailsBasedOnTransId(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal transActionId) {
		
		return iPromotionDAO.verifyRemittanceDetailsBasedOnTransId(appCountryId, companyId, documentCode, transActionId);
	}

	@Override
	@Transactional
	public void deletePromotionDetails(BigDecimal promotionDetailsId) {
		
		iPromotionDAO.deletePromotionDetails(promotionDetailsId);
	}

	@Override
	@Transactional
	public void savePromotionDeclaration(BigDecimal promotionDeclartionId,
			Clob custSignature, String userName) {
		
		iPromotionDAO.savePromotionDeclaration(promotionDeclartionId, custSignature, userName);
	}

	@Override
	@Transactional
	public PromotionDetails getPromotionDetailsReport(
			BigDecimal promotionDetailsId) {
	
		return iPromotionDAO.getPromotionDetailsReport(promotionDetailsId);
	}
	
	@Override
	@Transactional
	public List<PromotionDetails> getPromotionEnquiryDetails(BigDecimal promotionMasterId) {
		return iPromotionDAO.getPromotionEnquiryDetails(promotionMasterId);
	}

	@Override
	@Transactional
	public HashMap<String, List<PromotionPrize>> getPromotionPrizeDetailsForWinner(
			BigDecimal appicationCountryId, BigDecimal documentId,
			BigDecimal promotionMasterId) {
		
		return iPromotionDAO.getPromotionPrizeDetailsForWinner(appicationCountryId, documentId, promotionMasterId);
	}
	
	@Override
	@Transactional
	public List<PromotionDetails> promotionDetailsList(BigDecimal promotionPrizeSeq) {
		return iPromotionDAO.promotionDetailsList(promotionPrizeSeq);		
	}

	@Override
	@Transactional
	public BigDecimal getPromoUsedAmount(
			BigDecimal promotionDetailsId) {
		
		return iPromotionDAO.getPromoUsedAmount(promotionDetailsId);
	}
	
	@Override
	@Transactional
	public List<ViewRemittanceDocument> verifyRemittanceBasedOnTransId(BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,BigDecimal transActionId) {
		return iPromotionDAO.verifyRemittanceBasedOnTransId(appCountryId,companyId,documentCode,transActionId);
	}

	@Override
	@Transactional
	public List<PromotionPrize> getPrizeNoFromDate(BigDecimal promotionMasterId, Date fromDate) {
		
		return iPromotionDAO.getPrizeNoFromDate(promotionMasterId, fromDate);
	}

}
