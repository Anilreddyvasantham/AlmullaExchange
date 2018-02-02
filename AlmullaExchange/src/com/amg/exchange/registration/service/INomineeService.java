package com.amg.exchange.registration.service;

/**
 * "@author Arul JayaSingh
 */


import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.bean.NomineeBean;
import com.amg.exchange.common.service.IMutualService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.Nominee;


/**
 * @author exim07
 * @param <T>
 *
 */
public interface INomineeService<T> extends  IMutualService<T> {
	public List<Customer> getNomineeDetail(String civilID);
	public void saveNomineeDetail(Nominee nominee);
	public List<Nominee> getNomineeList(BigDecimal nominatorId);
	public void deleteNominee(NomineeBean nominee);

}
