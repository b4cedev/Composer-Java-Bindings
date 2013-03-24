package org.getcomposer.core.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.getcomposer.core.ComposerPackage;
import org.getcomposer.core.objects.Namespace;
import org.junit.Test;

public class AutoloadTest extends ComposertTestCase {
	
	@Test
	public void testPsr0() throws IOException, URISyntaxException {
		
		ComposerPackage composerPackage = new ComposerPackage(loadFile("autoload.json"));
		composerPackage.getAutoload().clearPsr0();
		Namespace ns = new Namespace();
		ns.setNamespace("foo");
		ns.add("bar");
		composerPackage.getAutoload().getPsr0().add(ns);
		assertEquals(1, composerPackage.getAutoload().getPsr0().size());
		Namespace namespace = composerPackage.getAutoload().getPsr0().get("foo");
		assertNotNull(namespace);
		assertEquals("bar", namespace.getAll().get(0));
	}
}
