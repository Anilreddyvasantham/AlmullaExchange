package com.amg.exchange.common.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@SuppressWarnings("serial")
/*@ManagedBean(name = "restClient")
@SessionScoped*/
@Component(value = "restClient")
@Scope("session")
public class RestClient implements Serializable {

	private static final Logger logger = Logger.getLogger(RestClient.class);
	private transient Client client;
	public static String STATUS_CODE = "statusCode";
	public static String STATUS_MSG = "statusMessage";
	public static String DATA = "data";

	public String SERVICE_BASE_URI;
	
	private String acceptType = "application/json";

	@PostConstruct
	protected void initialize() { 
		
		FacesContext fc = FacesContext.getCurrentInstance();
		SERVICE_BASE_URI = fc.getExternalContext().getInitParameter("webServiceURI");
		client = Client.create();
	}

	public WebResource getWebResource(String relativeUrl) {
		
		if (client == null) {
			initialize();
		}
		return client.resource(SERVICE_BASE_URI + relativeUrl);
	}

	public ClientResponse clientGetResponse(String relativeUrl) {
		
		WebResource webResource = getWebResource(relativeUrl);
		logger.info(webResource.getURI().toString());
		return webResource.accept(getAcceptType()).get(ClientResponse.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ClientResponse clientPostResponse(String relativeUrl, JSONObject postParams) {
		
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("params", postParams.toString());
		WebResource webResource = getWebResource(relativeUrl);
		logger.info(webResource.getURI().toString());
		return webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, formData);
	}
	
	//Call web service through post method
	public JSONObject postRequest(String uri, JSONObject postParams){
		
		JSONObject returnObject = new JSONObject();
		try{
			ClientResponse response = clientPostResponse(uri, postParams);
			if (response.getStatus() != 200) {
				logger.error("Failed service call: HTTP error code : "+response.getStatus());
			}else{
				returnObject = new JSONObject(response.getEntity(String.class));
			}
		}catch(ClientHandlerException e){
			logger.error("Client Handle Exception :: "+e); 
		}catch(UniformInterfaceException e){
			logger.error("Uniform Interface Exception :: "+e);
		}catch(JSONException e){
			logger.error("Error @ parse JSON Object :: "+e);
		}catch(Exception e){
			logger.error("Unknown exception :: "+e);
		}
		return returnObject;
	}
	
	public JSONObject getResponseObject(String uri){
		
		JSONObject returnObject = new JSONObject();
		try{
			ClientResponse response = clientGetResponse(uri);
	
	        if (response.getStatus() != 200) {
	            logger.error("Failed service call: HTTP error code : "+response.getStatus());
	        }else{
	        	returnObject = new JSONObject(response.getEntity(String.class));
	        }
		}catch(ClientHandlerException clientHandlerException){
			logger.error("Client Handle Exception");
		}catch(UniformInterfaceException uniformInterfaceException){
			logger.error("Uniform Interface Exception");
		}catch(JSONException jsonException){
			logger.error("Error @ parse JSON Object");
		}catch(Exception e){
			logger.error("Unknown exception :: "+e);
		}
		return returnObject;
	}

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}
}
