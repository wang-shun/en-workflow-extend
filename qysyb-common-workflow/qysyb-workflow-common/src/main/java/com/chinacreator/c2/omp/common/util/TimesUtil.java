package com.chinacreator.c2.omp.common.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimesUtil {
    
	//用来全局控制 上一周，本周，下一周的周数变化        
	private static int weeks = 0;        
	private static int MaxDate;//一月最大天数        
	private static int MaxYear;//一年最大天数        
	        
	/**      
	    * 得到二个日期间的间隔天数 
	    */       
	public static String getTwoDay(String sj1, String sj2) {        
	    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");        
	    long day = 0;        
	    try {        
	     java.util.Date date = myFormatter.parse(sj1);        
	     java.util.Date mydate = myFormatter.parse(sj2);        
	     day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);        
	    } catch (Exception e) {        
	     return "";        
	    }        
	    return day + "";        
	}         
	   
	/**      
	    * 根据一个日期，返回是星期几的字符串      
	    *       
	    * @param sdate      
	    * @return      
	    */       
	public static String getWeek(String sdate) {        
	    // 再转换为时间        
	    Date date = TimesUtil.strToDate(sdate);        
	    Calendar c = Calendar.getInstance();        
	    c.setTime(date);        
	    // int hour=c.get(Calendar.DAY_OF_WEEK);        
	    // hour中存的就是星期几了，其范围 1~7        
	    // 1=星期日 7=星期六，其他类推        
	    return new SimpleDateFormat("EEEE").format(c.getTime());        
	}        
	   
	/**      
	    * 将短时间格式字符串转换为时间 yyyy-MM-dd       
	    *       
	    * @param strDate      
	    * @return      
	    */       
	public static Date strToDate(String strDate) {        
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");        
	    ParsePosition pos = new ParsePosition(0);        
	    Date strtodate = formatter.parse(strDate, pos);        
	    return strtodate;        
	}        
	   
	/**
	 * 
	 *@title 将字符串转换为指定格式日期
	 *@param strDate
	 *@param dateFormat
	 *@return  Date
	 *@date 2016年1月12日
	 *@author tao.wang1
	 */
	public static Date strToDate(String strDate,String dateFormat) {        
	    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);        
	    ParsePosition pos = new ParsePosition(0);        
	    Date strtodate = formatter.parse(strDate, pos);        
	    return strtodate;        
	} 	
	
	/**      
	    * 两个时间之间的天数      
	    *       
	    * @param date1      
	    * @param date2      
	    * @return      
	    */       
	public static long getDays(String date1, String date2) {        
	    if (date1 == null || date1.equals(""))        
	     return 0;        
	    if (date2 == null || date2.equals(""))        
	     return 0;        
	    // 转换为标准时间        
	    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");        
	    java.util.Date date = null;        
	    java.util.Date mydate = null;        
	    try {        
	     date = myFormatter.parse(date1);        
	     mydate = myFormatter.parse(date2);        
	    } catch (Exception e) {        
	    }        
	    long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);        
	    return day;        
	}         
	        
	// 计算当月最后一天,返回字符串        
	public static String getDefaultDay(){          
	   String str = "";        
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");            
	   
	   Calendar lastDate = Calendar.getInstance();        
	   lastDate.set(Calendar.DATE,1);//设为当前月的1号        
	   lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号        
	   lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天        
	           
	   str=sdf.format(lastDate.getTime());        
	   return str;          
	} 
	// 计算当月最后一天,返回date     
	public static Date getLastDayMonth(){          
	   Calendar lastDate = Calendar.getInstance();        
	   lastDate.set(Calendar.DATE,1);//设为当前月的1号        
	   lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号        
	   lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天      
	   Date date=lastDate.getTime();
	           
	   return date;          
	} 
	        
	// 上月第一天        
	public static String getPreviousMonthFirst(){          
	   String str = "";        
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");            
	   
	   Calendar lastDate = Calendar.getInstance();        
	   lastDate.set(Calendar.DATE,1);//设为当前月的1号        
	   lastDate.add(Calendar.MONTH,-1);//减一个月，变为下月的1号        
	   //lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天        
	           
	   str=sdf.format(lastDate.getTime());        
	   return str;          
	}      
	// 某月第一天        
	public static Timestamp getMonthFirstDate(int monthNum){          
	   Calendar lastDate = Calendar.getInstance();        
	   lastDate.set(Calendar.DATE,1);//设为当前月的1号        
	   if(monthNum>0){
		   lastDate.add(Calendar.MONTH,-monthNum);//减月        
	   }
	   Timestamp resultTime=new Timestamp(lastDate.getTimeInMillis());
	   return resultTime;          
	} 	
	
	// 某月最后一天        
	public static Timestamp getMonthEndDate(int monthNum){   
		Calendar lastDate = Calendar.getInstance();        
		   lastDate.set(Calendar.DATE,1);//设为当前月的1号        
		   lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号        
		   if(monthNum>0){
			   lastDate.add(Calendar.MONTH,-monthNum);//减月        
		   }
		   lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天      
	   Timestamp resultTime=new Timestamp(lastDate.getTimeInMillis());
	   return resultTime;         
	} 		
	        
	//获取当月第一天        
	public static String getFirstDayOfMonth(){          
	   String str = "";        
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");            
	   
	   Calendar lastDate = Calendar.getInstance();        
	   lastDate.set(Calendar.DATE,1);//设为当前月的1号        
	   str=sdf.format(lastDate.getTime());        
	   return str;          
	}        
	      
	//获取当月第一天        
	public static Date getFirstDayMonth(){          
	   Calendar firstDate = Calendar.getInstance();        
	   firstDate.set(Calendar.DATE,1);//设为当前月的1号        
	   Date firstDay=firstDate.getTime();
	   return firstDay;          
	} 
	// 获得本周星期日的日期          
	public static String getCurrentWeekday() {        
	    weeks = 0;        
	    int mondayPlus = getMondayPlus();        
	    GregorianCalendar currentDate = new GregorianCalendar();        
	    currentDate.add(GregorianCalendar.DATE, mondayPlus+6);        
	    Date monday = currentDate.getTime();        
	            
	    DateFormat df = DateFormat.getDateInstance();        
	    String preMonday = df.format(monday);        
	    return preMonday;        
	}         
	        
	//获取当天时间         
	public static String getNowTime(String dateformat){        
	    Date   now   =   new   Date();           
	    SimpleDateFormat   dateFormat   =   new   SimpleDateFormat(dateformat);//可以方便地修改日期格式           
	    String  hehe  = dateFormat.format(now);           
	    return hehe;        
	} 
	
	//获取昨天时间         
	public static Date getPreviousDate(String dateformat){        
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
	    Calendar today = Calendar.getInstance();
	    today.add(Calendar.HOUR,-24);//减一天               
	    return strToDate(sdf.format(today.getTime()));        
	}
	
	//获取昨天时间         
	public static Date getPreviousDate(){        
	    Calendar today = Calendar.getInstance();
	    today.add(Calendar.HOUR,-24);//减一天               
	    return today.getTime();        
	}
	        
	// 获得当前日期与本周日相差的天数        
	private static int getMondayPlus() {        
	    Calendar cd = Calendar.getInstance();        
	    // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......        
	    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1        
	    if (dayOfWeek == 1) {        
	        return 0;        
	    } else {        
	        return 1 - dayOfWeek;        
	    }        
	}        
	        
	//获得本周一的日期        
	public static String getMondayOFWeek(){        
	     weeks = 0;        
	     int mondayPlus = getMondayPlus();        
	     GregorianCalendar currentDate = new GregorianCalendar();        
	     currentDate.add(GregorianCalendar.DATE, mondayPlus);        
	     Date monday = currentDate.getTime();        
	             
	     DateFormat df = DateFormat.getDateInstance();        
	     String preMonday = df.format(monday);        
	     return preMonday;        
	}        
	        
	//获得相应周的周六的日期        
	public static String getSaturday() {        
	    int mondayPlus = getMondayPlus();        
	    GregorianCalendar currentDate = new GregorianCalendar();        
	    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);        
	    Date monday = currentDate.getTime();        
	    DateFormat df = DateFormat.getDateInstance();        
	    String preMonday = df.format(monday);        
	    return preMonday;        
	}  
	
	
	
	// 获得上周星期日的日期        
	public static String getPreviousWeekSunday() {        
	    weeks=0;        
	    weeks--;        
	    int mondayPlus = getMondayPlus();        
	    GregorianCalendar currentDate = new GregorianCalendar();        
	    currentDate.add(GregorianCalendar.DATE, mondayPlus+weeks);        
	    Date monday = currentDate.getTime();        
	    DateFormat df = DateFormat.getDateInstance();        
	    String preMonday = df.format(monday);        
	    return preMonday;        
	}        
	   
	// 获得上周星期一的日期        
	public static String getPreviousWeekday() {        
	    weeks--;        
	    int mondayPlus = getMondayPlus();        
	    GregorianCalendar currentDate = new GregorianCalendar();        
	    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);        
	    Date monday = currentDate.getTime();        
	    DateFormat df = DateFormat.getDateInstance();        
	    String preMonday = df.format(monday);        
	    return preMonday;        
	}        
	        
	// 获得下周星期一的日期        
	public static String getNextMonday() {        
	    weeks++;        
	    int mondayPlus = getMondayPlus();        
	    GregorianCalendar currentDate = new GregorianCalendar();        
	    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);        
	    Date monday = currentDate.getTime();        
	    DateFormat df = DateFormat.getDateInstance();        
	    String preMonday = df.format(monday);        
	    return preMonday;        
	}        
	        
	// 获得下周星期日的日期        
	public static String getNextSunday() {        
	            
	    int mondayPlus = getMondayPlus();        
	    GregorianCalendar currentDate = new GregorianCalendar();        
	    currentDate.add(GregorianCalendar.DATE, mondayPlus + 7+6);        
	    Date monday = currentDate.getTime();        
	    DateFormat df = DateFormat.getDateInstance();        
	    String preMonday = df.format(monday);        
	    return preMonday;        
	}        
	        
	public static int getMonthPlus(){        
	    Calendar cd = Calendar.getInstance();        
	    int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);        
	    cd.set(Calendar.DATE, 1);//把日期设置为当月第一天         
	    cd.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天         
	    MaxDate=cd.get(Calendar.DATE);         
	    if(monthOfNumber == 1){        
	        return -MaxDate;        
	    }else{        
	        return 1-monthOfNumber;        
	    }        
	}        
	        
	//获得上月最后一天的日期        
	public static String getPreviousMonthEnd(){        
	    String str = "";        
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");            
	   
	   Calendar lastDate = Calendar.getInstance();        
	  lastDate.add(Calendar.MONTH,-1);//减一个月        
	  lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天         
	  lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天         
	   str=sdf.format(lastDate.getTime());        
	   return str;          
	}        
	        
	//获得下个月第一天的日期        
	public static String getNextMonthFirst(){        
	    String str = "";        
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");            
	   
	   Calendar lastDate = Calendar.getInstance();        
	  lastDate.add(Calendar.MONTH,1);//减一个月        
	  lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天         
	   str=sdf.format(lastDate.getTime());        
	   return str;          
	}        
	        
	//获得下个月最后一天的日期        
	public static String getNextMonthEnd(){        
	    String str = "";        
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");            
	   
	   Calendar lastDate = Calendar.getInstance();        
	  lastDate.add(Calendar.MONTH,1);//加一个月        
	  lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天         
	  lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天         
	   str=sdf.format(lastDate.getTime());        
	   return str;          
	}        
	        
	//获得明年最后一天的日期        
	public static String getNextYearEnd(){        
	    String str = "";        
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");            
	   
	  Calendar lastDate = Calendar.getInstance();        
	  lastDate.add(Calendar.YEAR,1);//加一个年        
	  lastDate.set(Calendar.DAY_OF_YEAR, 1);        
	 lastDate.roll(Calendar.DAY_OF_YEAR, -1);        
	   str=sdf.format(lastDate.getTime());        
	   return str;          
	}        
	        
	//获得明年第一天的日期        
	public static String getNextYearFirst(){        
	    String str = "";        
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");            
	   
	  Calendar lastDate = Calendar.getInstance();        
	  lastDate.add(Calendar.YEAR,1);//加一个年        
	  lastDate.set(Calendar.DAY_OF_YEAR, 1);        
	       str=sdf.format(lastDate.getTime());        
	  return str;          
	            
	}        
	        
	//获得本年有多少天        
	public static int getMaxYear(){        
	    Calendar cd = Calendar.getInstance();        
	    cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天        
	    cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。        
	    int MaxYear = cd.get(Calendar.DAY_OF_YEAR);         
	    return MaxYear;        
	}        
	        
	private static int getYearPlus(){        
	    Calendar cd = Calendar.getInstance();        
	    int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天        
	    cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天        
	    cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。        
	    int MaxYear = cd.get(Calendar.DAY_OF_YEAR);        
	    if(yearOfNumber == 1){        
	        return -MaxYear;        
	    }else{        
	        return 1-yearOfNumber;        
	    }        
	}        
	//获得本年第一天的日期        
	public static String getCurrentYearFirst(){        
	    int yearPlus = getYearPlus();        
	    GregorianCalendar currentDate = new GregorianCalendar();        
	    currentDate.add(GregorianCalendar.DATE,yearPlus);        
	    Date yearDay = currentDate.getTime();        
	    DateFormat df = DateFormat.getDateInstance();        
	    String preYearDay = df.format(yearDay);        
	    return preYearDay;        
	}        
	//获得本年最后一天的日期 *          
	public static String getCurrentYearEnd(){          
	  Date date = new Date();          
	  SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式             
	  String  years  = dateFormat.format(date);             
	  return years+"-12-31";          
	}          
	        
	//获得上年第一天的日期 *          
	public static String getPreviousYearFirst(){          
	  Date date = new Date();          
	  SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式             
	  String  years  = dateFormat.format(date); int years_value = Integer.parseInt(years);            
	  years_value--;          
	  return years_value+"-1-1";          
	}          
	        
	//获得上年最后一天的日期          
	public static String getPreviousYearEnd(){          
	  weeks--;          
	  int yearPlus = getYearPlus();          
	  GregorianCalendar currentDate = new GregorianCalendar();          
	  currentDate.add(GregorianCalendar.DATE,yearPlus+MaxYear*weeks+(MaxYear-1));          
	  Date yearDay = currentDate.getTime();          
	  DateFormat df = DateFormat.getDateInstance();          
	  String preYearDay = df.format(yearDay);          
	  getThisSeasonTime(11);          
	  return preYearDay;          
	}          
	        
	//获得本季度          
	public static String getThisSeasonTime(int month){          
	  int array[][] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};          
	  int season = 1;          
	  if(month>=1&&month<=3){          
	      season = 1;          
	  }          
	  if(month>=4&&month<=6){          
	      season = 2;          
	  }          
	  if(month>=7&&month<=9){          
	      season = 3;          
	  }          
	  if(month>=10&&month<=12){          
	      season = 4;          
	  }          
	  int start_month = array[season-1][0];          
	  int end_month = array[season-1][2];          
	            
	  Date date = new Date();          
	  SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式             
	  String  years  = dateFormat.format(date);             
	  int years_value = Integer.parseInt(years);          
	            
	  int start_days =1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);          
	  int end_days = getLastDayOfMonth(years_value,end_month);          
	  String seasonDate = years_value+"-"+start_month+"-"+start_days+";"+years_value+"-"+end_month+"-"+end_days;          
	  return seasonDate;          
	            
	}          
	        
	/**       
	* 获取某年某月的最后一天       
	* @param year 年       
	* @param month 月       
	* @return 最后一天       
	*/         
	private static int getLastDayOfMonth(int year, int month) {          
	   if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8         
	             || month == 10 || month == 12) {          
	         return 31;          
	   }          
	   if (month == 4 || month == 6 || month == 9 || month == 11) {          
	         return 30;          
	   }          
	   if (month == 2) {          
	         if (isLeapYear(year)) {          
	             return 29;          
	         } else {          
	             return 28;          
	         }          
	   }          
	   return 0;          
	}          
	/**       
	* 是否闰年       
	* @param year 年       
	* @return        
	*/         
	public static boolean isLeapYear(int year) {          
	  return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);          
	}   
	/**
	 * 获取日期字符串
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String datetimeStr = sdf.format(date);
		return datetimeStr;
	}
	/**
	 * 日期比较
	 * @param dat1
	 * @param dat2
	 * @return
	 */
	 public static boolean dateCompare(Date dat1, Date dat2) {
		  boolean dateComPareFlag = true;//dat2大
		  if (dat2.compareTo(dat1) != 1) {// dat2<=dat1
		   dateComPareFlag = false; //dat1大
		  }
		  return dateComPareFlag;
	}
	 /**
	  * 获取月最大天数
	  * @param date
	  * @return
	  */
	 public static int getMonthDays(Date date){
		 Calendar cal = new GregorianCalendar();  
		 cal.setTime(date);
		 int num = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
		 return num;
	}	 
	 /**
	  * 获取时间天
	  * @param date
	  * @return
	  */
	 public static int getMonthDaysByDate(Date date){
		 Calendar cal = new GregorianCalendar();  
		 cal.setTime(date);
		 int num = cal.get(Calendar.DAY_OF_MONTH);  
		 return num;
	}
	 
	 public static Date getFormatDate(String formatDate){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date datetime=null;//创建时间
			try {
				datetime = sdf.parse(formatDate);
			} catch (Exception e) {
				e.printStackTrace();
			}	
			return datetime;
	}
	 public static Date getDateToFormat(String formatDate,String format){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
	        Date datetime=null;//创建时间
			try {
				datetime = sdf.parse(formatDate);
			} catch (Exception e) {
				e.printStackTrace();
			}	
			return datetime;
	}	 
	 public static boolean isMonthFirstDay(){
		 boolean flag=false;
		 Calendar c=Calendar.getInstance();
		 int day=c.get(Calendar.DAY_OF_MONTH);
		 if(day==1) {
			 flag=true;
		 }
		 return flag;
	 }
	 /**
	  * 获取下一年今天
	  * @return
	  */
	 public static Date getNextYear(){
		 Calendar cal= new GregorianCalendar();
		 cal.setTime(new Date());
		 cal.add(GregorianCalendar.YEAR, 1);
		 SimpleDateFormat sdfT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
	     String datetimeStr = sdfD.format(cal.getTime());
	     datetimeStr=datetimeStr+" 23:59:59";
	     Date datetime=null;//创建时间
		 try {
			datetime = sdfT.parse(datetimeStr);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return datetime;
	 }
	 
	 /**
	  * 获取下一年今天
	  * @return
	  */
	 public static String longToStr(Long longTime,String dateFormat){
		 Date date = new Date(longTime);
		 SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	 }	 
	 /**
	  * 获取当前时间
	  * @return
	  */
	 public static String getCurrentTime(String dateFormat){
		 Date date = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);//设置日期格式
		return sdf.format(date);
	 }	
	 /**
	  * 
	  * <p>获取Timestamp类型当前时间</p>
	  * @param dateFormat
	  * @return
	  * @author tao.wang
	  * @data 2015-9-30
	  */
	 public static Timestamp getCurrentTimestamp(){
		 Timestamp time = new Timestamp(System.currentTimeMillis()); 
		return time;
	 }	
	 
	 public static long getTimeInMillSeconds(String timeStr) {
			try {
				if (timeStr.toLowerCase().endsWith("-ago")) {
					long cur = Calendar.getInstance().getTimeInMillis();
					int lastchar = timeStr.length() - 5;
					int interval = Integer.parseInt(timeStr.substring(0, lastchar));
					switch (timeStr.charAt(lastchar)) {
					case 's':
						break; // seconds
					case 'm':
						interval *= 60;
						break; // minutes
					case 'h':
						interval *= 3600;
						break; // hours
					case 'd':
						interval *= 3600 * 24;
						break; // days
					case 'w':
						interval *= 3600 * 24 * 7;
						break; // weeks
					case 'n':
						interval *= 3600 * 24 * 30;
						break; // months
					case 'y':
						interval *= 3600 * 24 * 365;
						break; // years
					default:
						break;
					}
					cur -= (interval * 1000l);
					return cur;
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd-HH:mm:ss");
					Date d = sdf.parse(timeStr);
					return d.getTime();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
	}
	 /** 
	     * 返回当月最后一天的日期 
	     */  
	    public static Date getLastDayOfMonth(Date date) {  
	        Calendar calendar = convert(date);  
	        calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));  
	        return calendar.getTime();  
	    } 
	    /** 
	     * 将日期转换为日历 
	     * @param date 日期 
	     * @return 日历 
	     */  
	    private static Calendar convert(Date date) {  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);  
	        return calendar;  
	    }  
}
