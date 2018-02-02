package com.amg.exchange.util;

import java.sql.Timestamp;

import java.util.*;
import java.util.Calendar;
import java.util.TimeZone;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * ExUtils class provide utility methods used across the Exchange application 
 * Mainly contains Date and String manipulation
 * @version         1.01 2011-02-16
 * @author          Sudha Mathew
 * @author          Mohamed Jabarullah 
 * 
 */

/* *****************************************************************************
 *							FILE CHANGE HISTORY
 * *****************************************************************************
 * Name			Date				Action
 * -----------------------------------------------------------------------------
 * Sudha	      10/05/2010	Generic private method created for
                                        adding month/day/year/etc based
                                        on int value passed as parameter.
                                        Wrapper classes for adding month,year
                                        and day
 * Sudha              26/04/2011        Added joda tim
 * Sudha              16/02/2012        Added Methods for string manipulations.
*******************************************************************************/
public class ExUtils {
    
    /* ---------------------------------------------------------------------------
                        DATE HELPER FUNCTIONS                                   */
    public static final String DATE_FORMAT_MASK_DEFAULT = "dd-MM-yyyy";
    public static final String DATE_FORMAT_MASK_TIME = "dd-MM-yyyy hh:mm.ss";

   
    /**
     * This is a utility helper to find diff between two oracle.jbo.domain.Dates
     * Returns diff in days
     * */
  
    /**
      * Helper-Wrapper method for dates
      * Using JODA 
     */ 
     public static DateTime addWeeksToJodaDate(DateTime dt, int n) {
        DateTime later = dt.plusWeeks(n); // 1 = 1 week 
        return later;
     }
     
    /**
     * This is a utility helper to find diff between two DATETIME
     *  start date and end date
     * Returns diff in days 
     * */
     public static int JodaDateDiff(DateTime date1, DateTime date2) {
        Days d = Days.daysBetween(date1, date2);
        int days = d.getDays();
        return days;
     }
     
    /**
       * @return oracle.joda time pattern
       * @param stamp (the original date in DateTime  format)
       * @param pattern (String pattern hh:mm:ss)
       */
        public static String formatDateTimeAsPattern(DateTime dt, String pattern){
            //System.out.print("pattern is  "+pattern+ " and date is "+dt);
            DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);  
            //System.out.println("here time format is "+fmt.print(dt));
            return fmt.print(dt);

      }
     
    /**
     * convert String Date to JODA dateTime
     * @return oracle.joda time 
     * @param  sDate (the String date )
     * @param  sPattern ( date format to convert to )
     */
     public static DateTime convertfromStringtoJODA(String sDate, String sPattern){
        DateTimeFormatter formatter = DateTimeFormat.forPattern(sPattern);
        DateTime dt = formatter.parseDateTime(sDate);
        return dt;
     }
     
    /**
     * Utility methods for String manipulation.
     */
    /* Things we are about to do */
    /*     -- We need to strip special charaters
        -- We need to convert to Upper case
        -- We need to strip the s at the end	
        -- trim spaces
    */
    /**
    * Method removeTrailingS returns a String after removing trailing s
    * 
    * @param  s   The string to remove trailing 's'
    * @return the striped string
    */
    public static String removeTrailingS(String s){
    
      //  System.out.println(s.replaceAll("[sS]$", "" ));
       
        return s.replaceAll( "[sS]$", "" );              /*test and see*/
    }
     
    /**
    * Method stripSpecialCharacters returns a clean representation of a 
    * string without any nonalphanumeric and special charaters.
    * 
    * @param  s   The string to clean up
    * @return the striped string
    */
    public static String stripSpecialCharacters(String s){
        //return s.replaceAll( "\\W", "" );                /*test and see*/
        return s.replaceAll("[^\\p{L}\\p{N}]", "");    
    }
    
    /**
    * Method removeSpecialCharacters returns a clean representation of a 
    * string without any nonalphanumeric and special charaters.
    * 
    * @param  s   The string to clean up
    * @return the striped string
    */
    public static String removeSpecialCharacters(String s){
        //return s.replaceAll( "\\W", "" );                /*test and see*/
        //return s.replaceAll("[\\p{P}\\p{S}]", "");    
         return s.replaceAll("[^\\p{L}\\p{Z}]", "");    // not a letter and not a space
    }
    
    /**
    * Method simplifyString is used by Security Questions.
    * It returns a string after the following manipulation
    *     convert to upper case   : 
    *     strip special charaters : stripSpecialCharacters
    *     remove s from the end   : 
    *     trim spaces
    * @param  s   The String to simplify
    * @return the simplified string.
    */
    public static String simplifyString(String s){
//        return removeTrailingS(stripSpecialCharacters(s.toUpperCase())).trim();
        /* first we split the strings on space*/
        String[] result = s.split("\\s");
        for (int x=0; x<result.length; x++){
            //System.out.println(result[x]);
            // remove the trailing s from each word
            result[x]=removeTrailingS(result[x]);               /* remove trailing s*/
            result[x]=stripSpecialCharacters(result[x].toUpperCase()).trim(); /* strip special characters and trim*/
        }
        String finalString = "";
        for(int x=0; x<result.length; x++){
            finalString= finalString+result[x];
        }
        return finalString;
    }
    
    public static String replaceChar(String str, String regex){
        return str.replaceAll(regex,"");
    }
    
    /**
     * Convert the characters to ASCII value
     * @param character character
     * @return ASCII value
     */
    public static int CharToASCII(final char character){
            return (int)character;
    }

    /**
     * Convert the ASCII value to character
     * @param ascii ascii value
     * @return character value
     */
    public static char ASCIIToChar(final int ascii){
            return (char)ascii;             
    }
    
    /**
     * Util to check if string contains arabic and alphabet characters
     * @param ascii ascii value
     * @return character value
     */
    public static boolean checkArabicEntryAplha(String s){
        for (int i = s.length() - 1; i >= 0; --i) {  
            if(CharToASCII(s.charAt(i))< 100 && CharToASCII(s.charAt(i)) != 32){
                return false;
            }
        }
        return true;             
    }
    
    /**
     * Util to check if string contains only arabic
     * @param ascii ascii value
     * @return character value
     */
    public static boolean checkArabicEntry(String s){
        int i = s.length() - 1;
        if(CharToASCII(s.trim().charAt(0))< 100 ){
            if(i > 0 && CharToASCII(s.trim().charAt(i))< 100){
                return false;
            }
        }
        return true;                 
    }
    /**
     * Util to check if string contains only english and alphabets
     * CHECK_ALL_ENG_ENTRY_ALPHABET
     * @param ascii ascii value
     * @return character value
     */
    public static boolean checkAllEnglishAlphaEntry(String s){
        for (int i = s.length() - 1; i >= 0; --i) {  
            if(CharToASCII(s.charAt(i)) > 65 && CharToASCII(s.charAt(i)) < 90 
               && CharToASCII(s.charAt(i)) != 32 ){
                return false;
            }
        }
        return true;                  
    }
    /*
     * Entry Should be in Alphabet or Numbers
     * Entry Should be in Letters  Numbers
     */
    public static boolean checkEngishAlpha(String s){
        for (int i = s.length() - 1; i >= 0; --i) {  
            if(CharToASCII(s.charAt(i)) > 65  && CharToASCII(s.charAt(i)) < 90  &&
               CharToASCII(s.charAt(i)) > 48  && CharToASCII(s.charAt(i)) < 57  &&
               (CharToASCII(s.charAt(i)) != 44 || CharToASCII(s.charAt(i))!=  45 ||
               CharToASCII(s.charAt(i)) != 46)&&
               CharToASCII(s.charAt(i)) != 32 ){
                return false;
            }
        }
        
        return true;    
    }
    
    /**
     * Util to check if string contains only english alphabets
     * @param ascii ascii value
     * @return character value
     */
    public static boolean checkEnglishEntry(String s){
        
        //for (int i = s.length() - 1; i >= 0; --i) {
           // System.out.println(CharToASCII(s.charAt(0)));
           if(s.length() > 0){
            if(CharToASCII(s.charAt(0)) > 100){
                return false;
            }
                return true;  
            }
            return false;
               
    }
    
    /**
     * Util to check if string contains only english alphabets
     * @param ascii ascii value
     * @return character value
     */
    public static boolean checkAllEnglishEntry(String s){
        for (int i = s.length() - 1; i >= 0; --i) {  
            if(CharToASCII(s.charAt(i)) > 100){
                return false;
            }
        }
        return true;         
    }
   
    /**
     * Util to check if string contains only english 
     * @param ascii ascii value
     * @return character value
     */
    public static boolean checkAlphaSlashEntry(String s){
        for (int i = s.length() - 1; i >= 0; --i) {  
            if(CharToASCII(s.charAt(i)) > 65  && CharToASCII(s.charAt(i)) < 90  &&
               CharToASCII(s.charAt(i)) > 48  && CharToASCII(s.charAt(i)) < 57  &&
               (CharToASCII(s.charAt(i)) != 44 || CharToASCII(s.charAt(i))!=  45 ||
               CharToASCII(s.charAt(i)) != 46 || CharToASCII(s.charAt(i))!=  47 )&&
               CharToASCII(s.charAt(i)) != 32 ){
                return false;
            }
        }
        return true;           
    }     
        
}
