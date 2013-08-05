package cn.jinbi.mybase;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.jinbi.base.R;
import cn.jinbi.httpurl.HttpDownload;
import cn.jinbi.network.IsInternet;
import cn.jinbi.time.CurrentTime;

public class WeatherForecastActivity extends Activity {
	private TextView weaterText;
	private Button refreshButton;
	private String url="http://dev.365jinbi.com/weather/";
	private CurrentTime time1;
	private CurrentTime time2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weaterforecast);
		weaterText = (TextView)findViewById(R.id.weaterText);
		refreshButton = (Button)findViewById(R.id.refreshButton);
		getWeater();
		refreshButton.setOnClickListener(new ClickListener());
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
	
	
	private void getWeater() {
		if (!IsInternet.isNetworkAvalible(getApplicationContext())){
			IsInternet.checkNetwork(WeatherForecastActivity.this);
		}
		else{
			HttpDownload httpDown = new HttpDownload();
			String text = httpDown.Download(url);
			weaterText.setText(text);
			weaterText.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
			if (text.contains("阴") || text.contains("多云"))
			{
				weaterText.setBackgroundResource(R.drawable.cloudy);
			}else if (text.contains("雷")){
				weaterText.setBackgroundResource(R.drawable.llightning);
			}else if (text.contains("晴")){
				weaterText.setBackgroundResource(R.drawable.sun);
			}else if (text.contains("雪")){
				weaterText.setBackgroundResource(R.drawable.snow);
			}
			weaterText.getBackground().setAlpha(200);
			time1 = new CurrentTime();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.weater_forecast, menu);
		return true;
	}

}
