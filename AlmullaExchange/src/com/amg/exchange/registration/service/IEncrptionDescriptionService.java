package com.amg.exchange.registration.service;

public interface IEncrptionDescriptionService<T> {
	
	public String getDECrypted(String userid,String password);
	public String getENCrypted(String userid,String password);
}
