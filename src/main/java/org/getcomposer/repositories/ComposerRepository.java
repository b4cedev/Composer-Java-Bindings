package org.getcomposer.repositories;

import java.util.Map;

import org.getcomposer.collection.Versions;
import org.getcomposer.entities.GenericEntity;
import org.json.simple.JSONObject;

/**
 * Represents a composer repository
 * 
 * @author Thomas Gossmann <gos.si>
 *
 */
public class ComposerRepository extends Repository {

	private Map<String, Versions> packages;
	private GenericEntity options = new GenericEntity();
	
	public ComposerRepository() {
		super("composer");
		listen();
	}
	
	protected void parse(Object obj) {
		if (obj instanceof JSONObject) {
			JSONObject json = (JSONObject) obj;
			
			if (json.containsKey("options")) {
				options.load(json.get("options"));
			}
		}
		
		super.parse(obj);
	}
	
	public Versions getVersions(String name) {
		if (packages.containsKey(name)) {
			return packages.get(name);
		}
		return null;
	}
	
	/**
	 * Returns the options entity
	 * 
	 * @return the options
	 */
	public GenericEntity getOptions() {
		return options;
	}
}
