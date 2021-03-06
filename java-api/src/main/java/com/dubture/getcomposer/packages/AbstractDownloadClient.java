package com.dubture.getcomposer.packages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

abstract public class AbstractDownloadClient {
	
	protected String baseUrl;
	protected boolean baseUrlParamEncoding = false;
	private Log log = LogFactory.getLog(AbstractDownloadClient.class);
	protected String filter = null;
	
	public AbstractDownloadClient() {
	}

	public AbstractDownloadClient(String baseUrl) {
		this();
		this.baseUrl = baseUrl;
	}
	
	public AbstractDownloadClient(String baseUrl, boolean baseUrlParamEncoding) {
		this();
		this.baseUrl = baseUrl;
		this.baseUrlParamEncoding = baseUrlParamEncoding;
	}
	
	/**
	 * Sets the base url. %s in the baseUrl will be replaced with the param.
	 * 
	 * @param baseUrl
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public void setBaseUrlParamEncoding(boolean baseUrlParamEncoding) {
		this.baseUrlParamEncoding = baseUrlParamEncoding;
	}
	
	public boolean getBaseUrlParamEncoding() {
		return baseUrlParamEncoding;
	}
	
	protected String createUrl(String param) {
		try {
			if (baseUrlParamEncoding) {
				param = URLEncoder.encode(param, "UTF-8");
			}
			String url = String.format(baseUrl, param);
			
			if (filter != null) {
				if (url.contains("?")) {
					url += "&type=" + filter;
				} else {
					url += "?type=" + filter;
				}
			}
			
			return url;
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return null;
	}
	
	public void setFilter(String filter) throws UnsupportedEncodingException {
		this.filter = URLEncoder.encode(filter, "UTF-8");
	}
}
