/*******************************************************************************
 * Copyright (c) 2012 The PDT Extension Group (https://github.com/pdt-eg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package com.dubture.getcomposer.core.collection;

import org.json.simple.JSONArray;

import com.dubture.getcomposer.core.entities.AbstractJsonArray;

/**
 * Represents the license property of a composer package
 * 
 * @see http://getcomposer.org/doc/04-schema.md#license
 * @author Thomas Gossmann <gos.si>
 */
public class License extends AbstractJsonArray<String> {

	public License() {
	}
	
	protected void parse(Object obj) {
		clear();
		if (obj instanceof JSONArray) {
			for (Object license : (JSONArray)obj) {
				add((String)license);
			}
		} else {
			add((String)obj);
		}
		
	}
	
	/**
	 * Adds a license.
	 * 
	 * @param license
	 * @return this
	 */
	public void add(String license) {
		if (!has(license)) {
			super.add(license);
		}
	}
	
//	/**
//	 * Removes a license.
//	 * 
//	 * @return this
//	 */
//	public void remove(String license) {
//		super.remove(license);
//	}
}
