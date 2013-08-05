package cn.jinbi.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.TextView;
import cn.jinbi.base.R;


public class IsInternet {
	public static boolean isNetworkAvalible(Context context){
		//获得网络状态管理器
		ConnectivityManager connectivityManager = (ConnectivityManager)context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null){
			return false;
		}else{
			//建立网络数组
			NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
			
			if (net_info != null){
				for (int i = 0;i < net_info.length;i++){
					//判断获得的网络状态是否是处于连接状态
					if (net_info[i].getState() == NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}
		return false;
	}
	//如果没有网络，则弹出网络设置对话框
	public static void checkNetwork(final Activity activity){
		if (!IsInternet.isNetworkAvalible(activity)){
			TextView msg = new TextView(activity);
			msg.setText(R.string.noNetwork);
			new AlertDialog.Builder(activity)
						   .setIcon(R.drawable.ic_launcher)
						   .setTitle("网络状态提示")
						   .setView(msg)
						   .setPositiveButton("确定", 
								   new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, 
											int whichButton) {
										activity.startActivityForResult(new Intent(
												Settings.ACTION_WIRELESS_SETTINGS),
												0);
									}
								}).create().show();
		}
	}
}
