package net.wasdev.twelvefactorapp.unit.test;

import org.junit.Assert;
import org.junit.Test;

import net.wasdev.twelvefactorapp.CloudantCredentials;

/**
 * This tests that sending GET, PUT and POST requests to the JaxrsHttpReceiver class
 * don't return exceptions.
 */

public class CredentialsTest {

	@Test
	public void testStandardParameters() throws Exception {
			CloudantCredentials cc = new CloudantCredentials("ross", "cheese", "cloudant://myDb", "VCAP_SERVICES");
			Assert.assertEquals("The username should be ross", "ross", cc.getUsername());
			Assert.assertEquals("The password should be cheese", "cheese", cc.getPassword());
			Assert.assertEquals("The url should be cloudant://myDb", "cloudant://myDb", cc.getUrl());
	}
}
