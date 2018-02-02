package com.amg.exchange.registration.dao;

/**
 * "@author Arul JayaSingh
 */


import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.bean.NomineeBean;
import com.amg.exchange.common.dao.IMutualDao;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.Nominee;


/**
 * @author exim07
 *
 */
public interface INomineeDao extends IMutualDao{
	public List<Customer> getNomineeDetail(String civilID);
	public void saveNomineeDetail(Nominee nominee);
	public List<Nominee> getNomineeList(BigDecimal nominatorId);
	public void deleteNominee(NomineeBean nominee);

}
