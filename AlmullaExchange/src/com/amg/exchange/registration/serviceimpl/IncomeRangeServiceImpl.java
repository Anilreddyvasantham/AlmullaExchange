/**
 * 
 */
package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IincomeRangeDao;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.service.IincomeRangeService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;

/**
 * @author Subramaniam
 *
 */
@SuppressWarnings("serial")
@Service("incomeRangeServiceImpl")
public class IncomeRangeServiceImpl<T> implements IincomeRangeService<T>,Serializable {

	/**
	 * 
	 */
	@Autowired
	IincomeRangeDao<T> incomeRangeDao;
	
	

	public IincomeRangeDao<T> getIncomeRangeDao() {
		return incomeRangeDao;
	}

	public void setIncomeRangeDao(IincomeRangeDao<T> incomeRangeDao) {
		this.incomeRangeDao = incomeRangeDao;
	}

	@Override
	@Transactional
	public void save(IncomeRangeMaster incomeRangeMaster){
		getIncomeRangeDao().save(incomeRangeMaster);
		
	}
	@Override
	@Transactional
	public void delete(IncomeRangeMaster incomeRangeMaster){
		getIncomeRangeDao().delete(incomeRangeMaster);
	}
	@Override
	@Transactional
	public String getArticleName(BigDecimal articleId , BigDecimal languageId) {
		
		return getIncomeRangeDao().getArticleName(articleId,languageId);
	}
	
	@Override
	@Transactional
	public String getArticleDetailsName(BigDecimal articleDetailsId,BigDecimal languageId) {
		return getIncomeRangeDao().getArticleDetailsName(articleDetailsId,languageId);
	}
	@Override
	@Transactional
	public String getCountryCurrencyCode(BigDecimal countryId){
		return getIncomeRangeDao().getCountryCurrencyCode(countryId);
	}
	@Override
	@Transactional
	public List<IncomeRangeMaster> getIncomeRangeList(){
		return getIncomeRangeDao().getIncomeRangeList();
	}
	
	@Override
	@Transactional
	public List<ArticleDetailsDesc> getArticleDetails(BigDecimal articleId){
		return getIncomeRangeDao().getArticleDetails(articleId);
	}
	@Override
	@Transactional
	public List<IncomeRangeMaster> getIncomeRange(BigDecimal incomeRangeId){
		return getIncomeRangeDao().getIncomeRange(incomeRangeId);
	}
	@Override
	@Transactional
	public List<ArticleDetailsDesc> getArticleDetailsDesc(BigDecimal articleId,BigDecimal languageId){
		return getIncomeRangeDao().getArticleDetailsDesc(articleId,languageId);
	}
	@Override
	@Transactional
	public List<ArticleDetails> getArticleId(BigDecimal articleDetailId){
		
		return getIncomeRangeDao().getArticleId(articleDetailId);
		
	}
	@Override
	@Transactional
	public boolean isExistIncomeRange(BigDecimal appCountryId,BigDecimal articleDetailId,BigDecimal fromAmount, BigDecimal toAmount){
		return getIncomeRangeDao().isExistIncomeRange(appCountryId, articleDetailId, fromAmount, toAmount);
	}

	@Override
	@Transactional
	public List<IncomeRangeMaster> getIncomeFRangeListforDetail(BigDecimal countryId, BigDecimal articleDetailId) {
		return getIncomeRangeDao().getIncomeFRangeListforDetail(countryId,articleDetailId);
	}
}
