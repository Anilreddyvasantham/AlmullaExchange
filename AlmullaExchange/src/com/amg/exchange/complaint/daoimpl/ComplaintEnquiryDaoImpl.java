package com.amg.exchange.complaint.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.complaint.dao.IComplaintEnquiryDao;
import com.amg.exchange.complaint.model.ComplaintEnquiryRemittanceView;
import com.amg.exchange.complaint.model.ComplaintPendingEnquiryView;

@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class ComplaintEnquiryDaoImpl extends CommonDaoImpl implements IComplaintEnquiryDao{

	  @Override
	  public List<ComplaintPendingEnquiryView> tofetchRecorsFromcomplaintView(BigDecimal locationId, BigDecimal receiptNumber) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintPendingEnquiryView.class, "complaintPendingEnquiryView");
		    criteria.add(Restrictions.eq("complaintPendingEnquiryView.locationId", locationId));
		    if(receiptNumber !=null){
		    criteria.add(Restrictions.eq("complaintPendingEnquiryView.complaintReference", receiptNumber));
		    }
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<ComplaintPendingEnquiryView> lstComplaintPendingEnquiryView = (List<ComplaintPendingEnquiryView>) findAll(criteria);
		    return lstComplaintPendingEnquiryView;
	  }

	@Override
	public List<ComplaintPendingEnquiryView> fetchRecorsFromcomplaintPendingView(
			BigDecimal complainReference) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintPendingEnquiryView.class, "complaintPendingEnquiryView");
		    criteria.add(Restrictions.eq("complaintPendingEnquiryView.complaintReference", complainReference));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<ComplaintPendingEnquiryView> lstComplaintPendingEnquiryView = (List<ComplaintPendingEnquiryView>) findAll(criteria);
		    return lstComplaintPendingEnquiryView;
	}

	@Override
	public List<ComplaintEnquiryRemittanceView> fetchRecorsFromcomplaintFollowupEnquiryView(
			BigDecimal complainReference) {
		 DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintEnquiryRemittanceView.class, "complaintEnquiryRemittanceView");
		    criteria.add(Restrictions.eq("complaintEnquiryRemittanceView.complaintRefeenceNumber", complainReference));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<ComplaintEnquiryRemittanceView> lstComplaintFollowupEnquiryView = (List<ComplaintEnquiryRemittanceView>) findAll(criteria);
		    return lstComplaintFollowupEnquiryView;
	}

	  
}
