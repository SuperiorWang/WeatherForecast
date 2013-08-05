package cn.jinbi.httpurl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**根据URL下载文件：前提是这个文件当中的内容是文本，函数的返回值就是这个文件中的内容
 * 1.创建一个URL对象
 * 2.通过URL对象，创建HttpURLConnection对象
 * 3.得到InputStream
 * 4.从InputStream当中读取文件
 * @author think
 *
 */
public class HttpDownload {
	private URL url;
	
	public String Download(String urlStr){
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		String line = null;
		try {
			url = new URL(urlStr);
			//基于Http协议连接对象
			HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
			//设置请求等待响应时间
			urlCon.setConnectTimeout(5000);
			//设置请求方式
			urlCon.setRequestMethod("GET");
			//如果请求的状态码等于200表示请求成功
			System.out.println("OK");
			if (urlCon.getResponseCode() == 200)
			{
				System.out.println("OK");
     			br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
				while ((line=br.readLine())!=null){
					System.out.println("OK");
					buffer.append(line);
				}
			}
		}  catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}
}
