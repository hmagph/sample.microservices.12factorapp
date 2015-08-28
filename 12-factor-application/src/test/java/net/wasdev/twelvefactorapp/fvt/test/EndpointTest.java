package net.wasdev.twelvefactorapp.fvt.test;

import static org.junit.Assert.*;
import net.wasdev.twelvefactorapp.ResponseHandler.RequestType;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assume;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This tests that sending GET, PUT and POST requests to the JaxrsHttpReceiver class
 * don't return exceptions.
 */

public class EndpointTest {

	private String port;
	
	private String testDatabase = "testing";
	
	private boolean databaseConfigured = false;
	
	@Before
	public void setup() {
		// Database config must be stored in local environment variables
		databaseConfigured = System.getenv("dbUsername") != null && System.getenv("dbPassword") != null
				&& System.getenv("dbUrl") != null;
		this.port = System.getProperty("liberty.test.port");
        String url = "http://localhost:" + this.port + "/12-factor-application/";
        System.out.println("Creating database");
        Entity<String> ent = Entity.entity(testDatabase, MediaType.APPLICATION_JSON);
        Response response = sendRequest(url, RequestType.PUT, ent);
        System.out.println("Creating database status: " + response.getStatus());
		response.close();
	}
	
	@After
	public void cleanUp() {
        String url = "http://localhost:" + this.port + "/12-factor-application/" + testDatabase;
        System.out.println("Deleting database");
        Response response = sendRequest(url, RequestType.DELETE);
        response.close();
	}
	
	@Test
	public void testGet() {
		Assume.assumeTrue(databaseConfigured);
        String url = "http://localhost:" + this.port + "/12-factor-application/";
        System.out.println("Testing " + url);
        Response response = sendRequest(url, RequestType.GET);
		String responseString = response.readEntity(String.class);
		int responseCode = response.getStatus();
		response.close();
        System.out.println("Returned " + responseString);
        assertTrue("Incorrect response code: " + responseCode + " Response string is " + responseString,
        		responseCode == 200);
        assertFalse("Database returned error: " + responseString, responseString.contains("error"));
	}
	
	@Test
	public void testGetDatabase() {
		Assume.assumeTrue(databaseConfigured);
        String url = "http://localhost:" + this.port + "/12-factor-application/" + testDatabase;
        System.out.println("Testing " + url);
        Response response = sendRequest(url, RequestType.GET);
		String responseString = response.readEntity(String.class);
		int responseCode = response.getStatus();
		response.close();
        System.out.println("Returned " + responseString);
        assertTrue("Incorrect response code: " + responseCode + " Response string is " + responseString,
        		responseCode == 200);
        assertFalse("Database returned error: " + responseString, responseString.contains("error"));
	}
	
	@Test
	public void testPost() {
		Assume.assumeTrue(databaseConfigured);
        String url = "http://localhost:" + this.port + "/12-factor-application/" + testDatabase;
        System.out.println("Testing " + url);
        JsonObject data = Json.createObjectBuilder().add("weather", "sunny").build();
        String dataString = data.toString();
        Entity<String> ent = Entity.entity(dataString, MediaType.APPLICATION_JSON);
        Response response = sendRequest(url, RequestType.POST, ent);
		String responseString = response.readEntity(String.class);
		int responseCode = response.getStatus();
		response.close();
        System.out.println("Returned " + responseString);
        assertTrue("Incorrect response code: " + responseCode + " Response string is " + responseString,
        		responseCode == 200);
        assertFalse("Database returned error: " + responseString, responseString.contains("error"));
	}
	
	// If the database configuration cannot be reached make sure the app fails gracefully
	@Test
	public void testDatabaseNotFound() {
		Assume.assumeFalse(databaseConfigured);
        String url = "http://localhost:" + this.port + "/12-factor-application/";
        System.out.println("Testing " + url);
        Response response = sendRequest(url, RequestType.GET);
		String responseString = response.readEntity(String.class);
		int responseCode = response.getStatus();
		response.close();
        System.out.println("Returned " + responseString);
        assertTrue("Incorrect response code: " + responseCode + " Response string is " + responseString,
        		responseCode == 503);
        assertTrue("Application returned error: " + responseString, responseString.contains("{\"Exception\":\"Database cannot be accessed at this time"));
	}
	
	public Response sendRequest(String url, RequestType requestType) {
		return sendRequest(url, requestType, null);
	}
	
	public Response sendRequest(String url, RequestType requestType, Entity<?> ent) {
		Client client = ClientBuilder.newClient();
		System.out.println("Testing " + url);
		WebTarget target = client.target(url);
		Invocation.Builder invoBuild  = target.request();
		Response response = invoBuild.build(requestType.toString(), ent).invoke();
		return response;
	}

}
