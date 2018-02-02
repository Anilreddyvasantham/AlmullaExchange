package com.amg.exchange.registration.dao;

public interface IEncryptionDescriptionDao<T> {
	public String getDECrypted(String userid,String password);
	public String getENCrypted(String userid,String password);
}
