/*******************************************************************************
 * Copyright (c) 2012 The PDT Extension Group (https://github.com/pdt-eg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.getcomposer.packagist;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.getcomposer.ComposerConstants;
import org.getcomposer.ComposerPackage;
import org.getcomposer.Resource;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class SearchResultDownloader extends Downloader {
	public SearchResultDownloader() {
		super(ComposerConstants.SEARCH_URL);
	}

	public List<ComposerPackage> searchPackages(String query)
			throws IOException {
		List<ComposerPackage> packages = new ArrayList<ComposerPackage>();
		setUrl(String.format(ComposerConstants.SEARCH_URL, URLEncoder.encode(query, "UTF-8")));

		SearchResult result = loadPackages(getUrl());
		
		if (result != null && result.results != null) {
			packages.addAll(result.results);
		}
		
		int limit = 5;
		int current = 0;

		// TODO: implement paging results
		while (result.next != null && result.next.length() > 0) {
			result = loadPackages(result.next);

			if ((result.results != null && result.results.size() == 0)
					|| result.next == null || current++ > limit) {
				break;
			}
			packages.addAll(result.results);
		}

		return packages;
	}

	protected SearchResult loadPackages(String url) throws IOException {

		setUrl(url);
		InputStream resource = downloadResource();
		InputStreamReader reader = new InputStreamReader(resource);
		JsonReader jsonReader = new JsonReader(reader);
		Gson gson = Resource.getBuilder();

		return gson.fromJson(jsonReader, SearchResult.class);
	}

	public static class SearchResult {
		public List<ComposerPackage> results;
		public String next;
		public String total;
	}
}
