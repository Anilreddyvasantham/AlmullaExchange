/**
 * 
 */
package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;

/**
 * @author Subramaniam
 *
 */
public interface IincomeRangeDao<T> {
	
	public void save(IncomeRangeMaster incomeRangeMaster);
	
	public void delete(IncomeRangeMaster incomeRangeMaster);
	
	public String getArticleName(BigDecimal articleId , BigDecimal languageId);
	
	public String getArticleDetailsName(BigDecimal articleDetailsId,BigDecimal languageId);
	
	public String getCountryCurrencyCode(BigDecimal countryId);
	
	public List<IncomeRangeMaster> getIncomeRangeList();
	
	public List<ArticleDetailsDesc> getArticleDetails(BigDecimal articleId);
	
	public List<IncomeRangeMaster> getIncomeRange(BigDecimal incomeRangeId);
	
	public List<ArticleDetailsDesc> getArticleDetailsDesc(BigDecimal articleId,BigDecimal languageId);
	
	public List<ArticleDetails> getArticleId(BigDecimal articleDetailId);
	
	public boolean isExistIncomeRange(BigDecimal appCountryId,BigDecimal articleDetailId,BigDecimal fromAmount, BigDecimal toAmount);

	public List<IncomeRangeMaster> getIncomeFRangeListforDetail(BigDecimal countryId, BigDecimal articleDetailId);

}
