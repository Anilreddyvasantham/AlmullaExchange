package com.amg.exchange.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;

public class ReadApplicationProp {

	public static HashMap<String, String> getPropertiesValue() {
		HashMap<String, String> hashmap = new HashMap<>();

		// create a new ResourceBundle with default locale
		ResourceBundle bundle = ResourceBundle.getBundle("applicationConstants");
		//hashmap.put("hello", "helloTest");
		Enumeration<String> enumValue = bundle.getKeys();

		while (enumValue.hasMoreElements()) {
			String key = enumValue.nextElement();
			String value = bundle.getString(key);
			hashmap.put(key, value);
			//System.out.println(key + ": " + value);
		}

		return hashmap;

	}

	public static void main(String[] args) {
		
		HashMap<String,String> h1= getPropertiesValue();
		
		for (Entry<String, String> hashKey : h1.entrySet()) {
			String s1 = hashKey.getKey();
			String s2 = hashKey.getValue();
			System.out.println(" KEY : " + s1);
			System.out.println(" VAL : " + s2);

		}
		
	}
}
