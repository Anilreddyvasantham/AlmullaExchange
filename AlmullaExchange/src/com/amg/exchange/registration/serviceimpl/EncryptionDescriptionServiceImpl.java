package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.IEncryptionDescriptionDao;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
@SuppressWarnings("serial")
@Service("encryptionDescriptionServiceImpl")
public class EncryptionDescriptionServiceImpl<T> implements IEncrptionDescriptionService<T>, Serializable {

	
	@Autowired
	IEncryptionDescriptionDao<T> encryptionDescriptionDao;
	@Override
	@Transactional
	public String getDECrypted(String userid, String password) {
		return encryptionDescriptionDao.getDECrypted(userid, password);
	}
	@Override
	@Transactional
	public String getENCrypted(String userid, String password) {
		return encryptionDescriptionDao.getENCrypted(userid, password);
	}
}
