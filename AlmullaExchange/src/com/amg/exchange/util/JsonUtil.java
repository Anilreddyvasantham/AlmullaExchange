package com.amg.exchange.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.primefaces.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil<T> {

	@SuppressWarnings({ "rawtypes" })
	public static Map parseMapObject(JSONObject jsonString) throws JsonParseException, JsonMappingException, IOException{
		
		Map map = (HashMap)new ObjectMapper().readValue(JsonUtil.extractData(jsonString), HashMap.class);
		return map;
	}

	@SuppressWarnings("unchecked")
	public void parseMap(String jsonString, Map<T,T> map) throws JsonParseException, JsonMappingException, IOException{
		
		map = (HashMap<T,T>)new ObjectMapper().readValue(jsonString, HashMap.class);
	}
	
	public static String extractData(JSONObject object){
		
		try{
			return object.getJSONObject("data").toString();
		}catch(Exception e){
			return object.toString();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map sortMapByValueAsc(Map unsortMap) {
		 
		List list = new LinkedList(unsortMap.entrySet());
 
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
                                       .compareTo(((Map.Entry) (o2)).getValue());
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map sortMapByValueDesc(Map unsortMap) {
		 
		List list = new LinkedList(unsortMap.entrySet());
 
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
                                       .compareTo(((Map.Entry) (o1)).getValue());
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
