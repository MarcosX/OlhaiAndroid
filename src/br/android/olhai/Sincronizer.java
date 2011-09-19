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

	private static final String ENDERECO_API = "http://dev.thiagodnf.com/olhai/index.php/olhai/api";

	public String getJSONFromAplication() throws ClientProtocolException,
			IOException {
		HttpEntity entity = conectarHttpEntity();
		StringBuffer sb = new StringBuffer();
		if (entity != null) {
			Scanner s = new Scanner(entity.getContent());
			s.useDelimiter(",");
			while (s.hasNext()) {
				sb.append(s.next()).append(",");
			}
		}
		return sb.toString();
	}

	private HttpEntity conectarHttpEntity() throws IOException,
			ClientProtocolException {
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
		String encode = ENDERECO_API + "?idUniversidade=1&data="
				+ format.format(new Date());
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(encode);
		HttpResponse response;
		response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		return entity;
	}
}
