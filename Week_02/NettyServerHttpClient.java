package test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class NettyServerHttpClient {
	
    public static void main(String[] args) throws Exception {
    	CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8808/test");
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity!=null) {
				String content = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
				System.out.println("Netty server response:" + content);
			}
		} finally {
			response.close();
		}
    }

}
