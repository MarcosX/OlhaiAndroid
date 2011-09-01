package br.android.olhai;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Sincronizer {

	private static final String enderecoApi = "http://thiagonascimento.info/olhai/index.php/olhai/api";

	public String getJSONFromAplication(/* String data , String idUniversidade */)
			throws ClientProtocolException, IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
		String encode = enderecoApi + "?idUniversidade=1&data="
				+ format.format(new Date());
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(encode);
		HttpResponse response;
		StringBuffer sb = new StringBuffer();
		response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			Scanner s = new Scanner(entity.getContent());
			s.useDelimiter(",");
			while (s.hasNext()) {
				sb.append(s.next()).append(",");
			}
		}
		return sb.toString();
	}
}
