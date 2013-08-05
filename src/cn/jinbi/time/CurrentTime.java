package cn.jinbi.time;

import java.util.Calendar;

/**获取当前时间
 * @author think
 *
 */
public class CurrentTime {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private Calendar calendar;
	public CurrentTime(){
		calendar = Calendar.getInstance();
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.hour = calendar.get(Calendar.HOUR);
		this.minute = calendar.get(Calendar.MINUTE);
	}
	
	/**比较两个时间的时间差
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static Boolean timeDifference(CurrentTime time1,CurrentTime time2){
		if (time1.year != time2.year){
			System.out.println("执行1");
			return true;
		}else if (time1.month != time2.month){
			System.out.println("执行2");
			return true;
		}else if (time1.day != time2.day){
			System.out.println("执行3");
			return true;
		}else if (time1.hour != time2.hour){
			System.out.println("执行4");
			return true;
		}else if (Math.abs(time1.minute - time2.minute) >5){
			System.out.println("执行5");
			return true;
		}else{
			System.out.println("执行6");
			return false;
		}
	}
}
