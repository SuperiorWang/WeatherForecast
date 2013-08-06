package cn.jinbi.mybase;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.jinbi.base.R;
import cn.jinbi.httpurl.HttpDownload;
import cn.jinbi.jsonxml.JsonXml;
import cn.jinbi.network.IsInternet;
import cn.jinbi.time.CurrentTime;



public class WeatherForecastActivity extends Activity {
	private TextView time;
	private TextView city;
	private TextView characters;
	private TextView temperature;
	private TextView icon;
	private TextView temperature_text;
	private TextView icon_text;
	private ProgressBar bar;
	private String url="http://www.weather.com.cn/data/cityinfo/101210301.html";
	private CurrentTime time1;
	private CurrentTime time2;
	private String currentTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weaterforecast);
		time = (TextView)findViewById(R.id.time);
		city = (TextView)findViewById(R.id.city);
		characters = (TextView)findViewById(R.id.characters);
		temperature = (TextView)findViewById(R.id.temperature);
		icon = (TextView)findViewById(R.id.icon);
		icon_text = (TextView)findViewById(R.id.icon_text);
		temperature_text = (TextView)findViewById(R.id.temperature_text);
		bar = (ProgressBar)findViewById(R.id.progressBar);
		
		currentTime = "当前/"+ week().toString();
		time.setText(currentTime);
		
		getWeater();
		bar.setOnClickListener(new ClickListener());
	}

	public final class ClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			time2 = new CurrentTime();
			if (CurrentTime.timeDifference(time2, time1)){
				time1 = time2;
				getWeater();
				Toast.makeText(getApplicationContext(), R.string.freshSuccess, Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getApplicationContext(), R.string.freshFailed, Toast.LENGTH_LONG).show();
			}
		}
		
	}

	/**获取本日是星期几
	 * @return
	 */
	private String week(){
		Calendar calendar = Calendar.getInstance();
		Integer wek = Integer.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
		String mwek = null;
		switch (wek){
		case 1:
			mwek = "周日";
			break;
		case 2:
			mwek = "周一";
			break;
		case 3:
			mwek = "周二";
			break;
		case 4:
			mwek = "周三";
			break;
		case 5:
			mwek = "周四";
			break;
		case 6:
			mwek = "周五";
			break;
		case 7:
			mwek = "周六";
			break;
		default:
			System.out.println("星期计算错误");
			break;
		}
		return mwek;
	}
	
	private void getWeater() {
		if (!IsInternet.isNetworkAvalible(getApplicationContext())){
			IsInternet.checkNetwork(WeatherForecastActivity.this);
		}
		else{
			HttpDownload httpDown = new HttpDownload();
			String text = httpDown.Download(url);
			JsonXml json = new JsonXml(text);
			json.parseJson();
			String weatherCod = json.getWeather();
			String index;
			String weather = json.getTemp1();
			if (weather.compareTo("30\u2013")>0){
				index = "今日高温";
			}else if (weather.compareTo("15\u2013")>0){
				index = "今日舒适";
			}else if (weather.compareTo("5\u2013")>0){
				index = "今日微寒";
			}else{
				index = "今日寒冷";
			}
			SpannableString span = new SpannableString(index);
			span.setSpan(new AbsoluteSizeSpan(100), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
			characters.setText(span);
			
			String tmp = json.getTemp2()+"~"+json.getTemp1() ;
			temperature_text.setText(tmp);
			
			SpannableString sps = new SpannableString(json.getTemp1());
			sps.setSpan(new AbsoluteSizeSpan(80), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
			temperature.setText(sps);
			
			if (weatherCod.equals("阴") || weatherCod.equals("多云"))
			{
				icon.setBackgroundResource(R.drawable.cloudy);
				icon_text.setText("阴");
			}else if (weatherCod.contains("雷")){
				icon.setBackgroundResource(R.drawable.thunderstorm);
				icon_text.setText("雷雨");
			}else if (weatherCod.contains("晴")){
				icon.setBackgroundResource(R.drawable.sun);
				icon_text.setText("晴");
			}else if (weatherCod.contains("雨加雪")){
				icon.setBackgroundResource(R.drawable.snow);
				icon_text.setText("雨雪");
			}else if (weatherCod.contains("雪")){
				icon.setBackgroundResource(R.drawable.snowandrain);
				icon_text.setText("雪");
			}else if (weatherCod.contains("雨")){
				icon.setBackgroundResource(R.drawable.rain);
				icon_text.setText("雨");
			}
			time1 = new CurrentTime();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.weater_forecast, menu);
		return true;
	}

}
