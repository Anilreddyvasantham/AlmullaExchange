package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IEncryptionDescriptionDao;
@SuppressWarnings("serial")
@Repository
public class EncryptionDescriptionDaoImpl<T> extends CommonDaoImpl<T> implements IEncryptionDescriptionDao<T>, Serializable{

	@Override
	public String getDECrypted(String userid, String password) {

        
	    String salt = new StringBuffer(userid).reverse().toString();
	    salt = salt.substring(0,4);
	    String key = "almullaexchangeonlineremitt2010" + salt;
	    
	    Connection con = null;
	   
	    String Decrypted = null;
	     CallableStatement c = null;
	     try{
	    	 con = getDataSourceFromHibernateSession();
	        String proc = "Begin ? := PKG_ENCRYPT.decrypt_string ( ?, ?); End;";
	         c = con.prepareCall(proc);
	        

	        // Decrypt the password to compare with users password
	        c.setString(2, password);
	        c.setString(3, key );
	        c.registerOutParameter(1, Types.VARCHAR); // Encrypted
	        c.execute();
	        
	        Decrypted = c.getString(1);
	        c.close();
	       } catch (Exception e) {
	            e.printStackTrace();
	         }finally{
	            try{ if(c!=null){
	                c.close();
	            }
	            if(con!=null){
	                con.close();
	            }
	             
	              }catch(SQLException e){
	                e.printStackTrace();
	              }
	            }
	     return Decrypted;       
	 
	}

	@Override
	public String getENCrypted(String userid, String password) {

        
	      String salt = new StringBuffer(userid).reverse().toString();
	      salt = salt.substring(0,4);
	      String key = "almullaexchangeonlineremitt2010" + salt;
	      String encrypted = null;
	      
	      Connection con = null;
	  
	        CallableStatement c=null;
	       try{
	    	   con = getDataSourceFromHibernateSession();
	          String proc = "Begin ? := PKG_ENCRYPT.encrypt_string ( ? , ? ); End;";
	           c = con.prepareCall(proc);
	          String resultString;

	          c.setString(2, password);
	          c.setString(3, key);
	          c.registerOutParameter(1, Types.VARCHAR);
	          c.execute();
	          encrypted = c.getString(1);
	          c.close();
	         } catch (Exception e) {
	              e.printStackTrace();
	           }finally{
	            try{ if(c!=null){
	                c.close();
	            }
	            if(con!=null){
	                con.close();
	            }
	             
	              }catch(SQLException e){
	                e.printStackTrace();
	              }
	            }
	        return encrypted;
	    }

}
