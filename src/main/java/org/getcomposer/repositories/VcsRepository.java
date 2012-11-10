package org.getcomposer.repositories;

import java.io.File;
import java.io.FileNotFoundException;

import org.getcomposer.serialization.ClientEntitySerializer;

public class VcsRepository extends Repository {

	public VcsRepository() {
		super("vcs");
	}
	
	public VcsRepository(String type) {
		super(type);
	}
	
	public static VcsRepository fromFile(File input) throws FileNotFoundException {
		return fromFile(input, VcsRepository.class);
	}

	public static VcsRepository fromJson(String json) throws FileNotFoundException {
		return fromJson(json, VcsRepository.class);
	}
	
	public static Object getSerializer() {
		return new ClientEntitySerializer<VcsRepository>();
	}
}
