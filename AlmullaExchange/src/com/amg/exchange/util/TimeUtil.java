package com.amg.exchange.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	@SuppressWarnings("deprecation")
	public static String dateConverter(Date indate)
	{
		return ((indate.getYear())+1900)+"-"+(indate.getMonth()+1)+"-"+indate.getDate()+" "+indate.getHours()+":"+indate.getMinutes()+":"+indate.getSeconds();
	}
	
    public static double calcMiniutes(String logout,String login)
    {
    	double differ=0;
    	String lin=login.substring(0, 10);
    	String lin1=login.substring(11);
    	String lint[]=lin.split("-");
    	String lint1[]=lin1.split(":");	    	
      	String lout=logout.substring(0, 10);
    	String lout1=logout.substring(11);
    	String loutt[]=lout.split("-");
    	String loutt1[]=lout1.split(":");
    	
    	double lginyear=0,lginmtn=0,lginday=0,lginhr=0, lginmin=0,lginsec=0;
    	double lgotyear=0,lgotmtn=0,lgotday=0,lgothr=0, lgotmin=0,lgotsec=0;
    	
    	double year=0,month=0,day=0,hour=0,minute=0,second=0;
    	
    	lginyear=Double.parseDouble(lint[0]);
    	lginmtn=Double.parseDouble(lint[1]);
    	lginday=Double.parseDouble(lint[2]);
    	
    	lginhr=Double.parseDouble(lint1[0]);
    	lginmin=Double.parseDouble(lint1[1]);
    	lginsec=Double.parseDouble(lint1[2]);
    	
    	lgotyear=Double.parseDouble(loutt[0]);
    	lgotmtn=Double.parseDouble(loutt[1]);
    	lgotday=Double.parseDouble(loutt[2]);
    	
    	lgothr=Double.parseDouble(loutt1[0]);
    	lgotmin=Double.parseDouble(loutt1[1]);
    	lgotsec=Double.parseDouble(loutt1[2]);
    
    	year=subcalc(lgotyear,lginyear)*525960;
    	month=subcalc(lgotmtn, lginmtn)*43830;
    	day=subcalc(lgotday,lginday)*1440;
    	hour=subcalc(lgothr,lginhr)*60;
    	minute=subcalc(lgotmin,lginmin);
    	second=subcalc(lgotsec,lginsec)/60;
    	differ=Math.round(year+month+day+hour+minute+second);
    	return differ;
    }
    public static double subcalc(double lot, double lin)
    {
    	double output=0;
    	if(lin==lot)
    	{
    		output=0;
    	}
    	else if(lin>lot)
    	{
    		output=lot-lin;
    	}
    	else if(lin<lot)
    	{
    		output=lot-lin;
    	}	    	
    	return output;
    }
	
    
    
    public static String calcLogoutTime(String logout_time, String timeoutMinutes)
    {
		int lhr,lmin;
		int timeout_minutes=Integer.parseInt(timeoutMinutes);
		lhr=Integer.parseInt(logout_time.substring(11,13));
		lmin=Integer.parseInt(logout_time.substring(14,16));
		
		if(lmin>timeout_minutes){
			lmin=lmin-timeout_minutes;
			logout_time=logout_time.substring(0,14)+lmin+":"+logout_time.substring(17,19);
		}else{
			lmin=(lmin+60)-timeout_minutes;
			lhr=lhr-1;
			logout_time=logout_time.substring(0,10)+" "+lhr+":"+lmin+":"+logout_time.substring(17,19);
		}   	
		return logout_time;
    }
    
    
    public static void main(String arg[])
    {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		@SuppressWarnings("unused")
		String logout_time=sdf.format(new Date());
    }
    
}
