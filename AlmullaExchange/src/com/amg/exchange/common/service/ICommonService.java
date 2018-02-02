package com.amg.exchange.common.service;

public interface ICommonService <T> {

	void save (T entity);
	
	void update(T entity);

	void delete(T entity);
	
}
