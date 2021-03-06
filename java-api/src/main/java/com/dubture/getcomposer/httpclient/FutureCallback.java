package com.dubture.getcomposer.httpclient;

import org.apache.http.HttpResponse;

/**
 * Used as an adapter for HttpAsyncClient until it becomes available
 * as a stable version in eclipse orbit updatesite. 
 * 
 * @see http://hc.apache.org/httpcomponents-asyncclient-dev/httpasyncclient/apidocs/overview-summary.html
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public interface FutureCallback<T> {

	public void failed(Exception e);

	public void completed(HttpResponse response);

	public void cancelled();

}
