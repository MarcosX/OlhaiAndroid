package br.android.olhai;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Sincronizer {
	
	
	public String getJSONFromAplication(/*String idUniversidade,String data*/) throws ClientProtocolException, IOException{
		String encode = "http://thiagonascimento.info/olhai/index.php/olhai/api?idUniversidade=1&data=2011-08-01";
		//String url = "http://thiagonascimento.info/olhai/index.php/olhai/api?idUniversidade="; //idUniversidade + "&data=" + data;
		//String url2 = "&data=";
		HttpClient httpClient = new DefaultHttpClient();
		//String encode = url + URLEncoder.encode(idUniversidade) + url2 + URLEncoder.encode(data);
		HttpGet httpGet = new HttpGet(encode);
		HttpResponse response;
		StringBuffer sb = new StringBuffer();
		response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if(entity != null){
			Scanner s = new Scanner(entity.getContent());
			while(s.hasNext()){
				sb.append(s.next());
			}
		}
		
		return sb.toString();
	}
}
