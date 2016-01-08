package lw.droid.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

public class HttpResult {
	HttpResponse response;
	String responseString;
	int statusCode;
	public HttpResult(HttpResponse response,String responseString,int code) {
		super();
		this.response = response;
		this.responseString = responseString;
		this.statusCode = code;
	}

	public String getResponseString() throws ParseException, IOException
	{
		return responseString;
	}

	/**
	 * @return the response
	 */
	public HttpResponse getResponse() {
		return response;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	
	public boolean isOk()
	{
		return this.statusCode == 200 ;
	}
	
}
