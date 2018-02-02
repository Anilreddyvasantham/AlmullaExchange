package com.amg.exchange.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public final class CommonUtil {

	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			DATE_FORMAT);

	/**
	 * This method is return true if the object is meaningfully null. if the
	 * input object is string then it is also checked if the string is empty. If
	 * the input object is a collection it is checked if its size if greater
	 * than zero
	 * 
	 * @param object
	 *            - The Object which has to be checked for null
	 * @return true if the object is null
	 */
	@SuppressWarnings("unchecked")
	public static boolean isNull(Object object) {
		if (object == null) {
			return true;
		} else if (object instanceof String) {
			return (((String) object).trim().length() == 0);
		} else if (object instanceof Collection) {
			return (((Collection) object).size() == 0);
		} else {
			return false;
		}
	}

	/**
	 * This method returns true if the object is not null
	 * 
	 * @param object
	 *            - The object has to be checked if it is not null
	 * @return true if the object is not null
	 */
	public static boolean isNotNull(Object object) {
		return (!isNull(object));
	}

	/**
	 * This method checks if two given strings are equal. In specific two
	 * strings are considered equal in the following cases: <br>
	 * a) One string is null while other is empty<br>
	 * b) Both strings are empty<br>
	 * c) Both strings are null<br>
	 * d) Both strings have same value<br>
	 * 
	 * @param object1
	 *            , object2
	 * @return true in the above mentioned situations.
	 */
	public static boolean isEqual(Object object1, Object object2) {
		if (object1 instanceof String && object2 instanceof String) {
			return getEmptyIfNull(object1).equals(object2);
		} else {
			return ((object1 == null && object2 == null) ? true
					: ((object1 == null || object2 == null) ? false
							: ((object1 == object2) ? true : object1
									.equals(object2))));
		}
	}

	/**
	 * This method returns the second string if the first string is either empty
	 * or is null. This method can be used for default_value built-in in forms
	 * 
	 * @param sourceString
	 *            - The string to be checked for null
	 * @param targetString
	 *            - The string to be returned if the first string is either null
	 *            or empty
	 * 
	 * @return targetString if sourceString is null or empty
	 */
	public static String convertIfNull(Object sourceString, Object targetString) {
		return isNull(sourceString) ? targetString.toString() : sourceString
				.toString();
	}

	/**
	 * This method returns an empty string if the string passed is null
	 * otherwise returns the same string
	 * 
	 * @param sourceString
	 *            - The string to be checked for null
	 * @return empty string if sourceString is null, otherwise returns
	 *         sourceString
	 */
	public static String getEmptyIfNull(Object sourceString) {
		return convertIfNull(sourceString, "");
	}

	/*
	 * Method is used to trim the filed
	 * 
	 * @param paramValue
	 */
	public static String rightAndLeftTrim(String paramValue) {
		return paramValue == null ? paramValue : paramValue.replaceAll("^\\s+",
				"");
	}

	public static boolean isFileExists(String fileName) {
		File file = new File(fileName);
		if (!file.exists() || !file.isFile() || file.isHidden()) {
			return false;
		}
		return true;
	}

	/**
	 * Check given date is given format
	 * 
	 * @param date
	 *            - The date to be checked with dateFormat
	 * @return It returns true if given date match with given date format
	 *         pattern
	 */
	public static Date convertStringToDate(String date)
			throws AlmullExchnageServiceException {

		if (date.trim().length() != dateFormat.toPattern().length()) {
			throw new AlmullExchnageServiceException(
					"Date not in expected date format:" + DATE_FORMAT);
		}
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(date);
		} catch (ParseException parseException) {
			throw new AlmullExchnageServiceException(
					"Date not in expected date format:" + DATE_FORMAT);
		}
	}

	public static String readFile(String fileName)
			throws AlmullExchnageServiceException {

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fr);
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			throw new AlmullExchnageServiceException(e.getMessage());
		}
	}

	public static boolean validateApprovedBy(String user1, String user2) {

		if (user1.equalsIgnoreCase(user2)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean checkSizeOfRecords(List<?> object) {
		if (object.size() > 0) {
			return false;
		} else
			return true;
	}

	public static List<?> nullCheck(List<?> objectlist) {
			isNotNull(objectlist);
			return objectlist;
	}
	
	
	public static String getCurrentClassAndMethodNames() {
	    final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
	    final String s = e.getClassName();
	    return s.substring(s.lastIndexOf('.') + 1, s.length()) + "." + e.getMethodName();
	}
	
	
	private static Long dayToMiliseconds(int days) {
		Long result = Long.valueOf(days * 24 * 60 * 60 * 1000);
		return result;
	}

	public static Timestamp addDays(int days, Timestamp t1) throws Exception {
		if (days < 0) {
			throw new Exception("Day in wrong format.");
		}
		Long miliseconds = dayToMiliseconds(days);
		return new Timestamp(t1.getTime() + miliseconds);
	}
	
	

}
