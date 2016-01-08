package lw.droid.http;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.*;

import android.content.Context;
import android.util.Log;
public class HttpEngine {


	

		String url_;
	
		String regUrl_;

		/**
		 * constructor
		 * 
		 * @param url
		 *            URL of web service endpoint
		 */
		public HttpEngine() {
//			regUrl_ = AflConfig.getInstance().getEntry("REGISTRATION_URL", null);
//
//			url_ = AflConfig.getInstance().getEntry("AFL_WS_URL", null);
//			if (url_ == null)
//				throw new AflConfigException("AFL_WS_URL not specified");
		}

		public void ProcessResponse(String response) {
			// Log.d("WsComEngine.ProcessResponse", response);
		}

		HttpClient mHttpClient;

		private HttpClient createHttpClient() {
			if (mHttpClient != null)
				return mHttpClient;
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params,
					HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);

			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			SSLSocketFactory sf = getSSLSocketFactory();
			schReg.register(new Scheme("https", sf, 443));
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);

			mHttpClient = new DefaultHttpClient(conMgr, params);

			return mHttpClient;
		}

		SSLSocketFactory mSocketFactory;

		private SSLSocketFactory getSSLSocketFactory() {
			if (mSocketFactory != null)
				return mSocketFactory;

			mSocketFactory = SSLSocketFactory.getSocketFactory();

			KeyStore trustStore;
			try {
				trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

				trustStore.load(null, null);
				mSocketFactory = new MySSLSocketFactory(trustStore);

				mSocketFactory
						.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				Log.e("getSSLSocketFactory",
						e.getLocalizedMessage(), e);

			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				Log.e("getSSLSocketFactory",
						e.getLocalizedMessage(), e);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("getSSLSocketFactory",
						e.getLocalizedMessage(), e);

			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				Log.e("getSSLSocketFactory",
						e.getLocalizedMessage(), e);

			} catch (KeyManagementException e) {
				Log.e("getSSLSocketFactory",
						e.getLocalizedMessage(), e);

			} catch (UnrecoverableKeyException e) {
				Log.e("getSSLSocketFactory",
						e.getLocalizedMessage(), e);

			}
			return mSocketFactory;

		}

		public class MySSLSocketFactory extends SSLSocketFactory {
			SSLContext sslContext = SSLContext.getInstance("TLS");

			public MySSLSocketFactory(KeyStore truststore)
					throws NoSuchAlgorithmException, KeyManagementException,
					KeyStoreException, UnrecoverableKeyException {
				super(truststore);

				TrustManager tm = new X509TrustManager() {
					public void checkClientTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {

					}

					public void checkServerTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {

					}

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
				};

				sslContext.init(null, new TrustManager[] { tm }, null);
			}

			@Override
			public Socket createSocket(Socket socket, String host, int port,
					boolean autoClose) throws IOException, UnknownHostException {
				return sslContext.getSocketFactory().createSocket(socket, host,
						port, autoClose);
			}

			@Override
			public Socket createSocket() throws IOException {
				return sslContext.getSocketFactory().createSocket();
			}
		}

		

		public HttpResult post(String url, String request,
				SyncProcessor processor) throws ComException {
			HttpClient httpclient = createHttpClient();
			HttpPost httppost = new HttpPost(url);

			try {

			

				 String jsonData = request;
			        httppost.setHeader("Content-type", "application/json");
			        httppost.setHeader("charset", "utf-8");
			        StringEntity se = new StringEntity(jsonData,"UTF-8"); 
			        
			        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			    //    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "utf-8"));
			    
			    
			        httppost.setEntity(se); 

			        HttpResponse response = httpclient.execute(httppost);
			        
			        String responseString = EntityUtils.toString(response.getEntity());
				
				HttpResult rv = new HttpResult(response,responseString,response.getStatusLine().getStatusCode());
				return rv;

			} catch (Exception ex) {
			
				throw new ComException("WsComService.AskServer", ex);
			}

		}

		public String get(String url,
				SyncProcessor processor) throws ComException {
			HttpClient httpclient = createHttpClient();
			HttpGet httpget = new HttpGet(url);

			try {

			

				 
			        httpget.setHeader("Content-type", "application/json");

			       // StringEntity se = new StringEntity(jsonData); 
			       // se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			      //  httppost.setEntity(se); 

			        HttpResponse response = httpclient.execute(httpget);
			        String responseString = EntityUtils.toString(response.getEntity());
				
				
				return responseString;

			} catch (Exception ex) {
			
				throw new ComException("WsComService.AskServer", ex);
			}

		}

	
}
