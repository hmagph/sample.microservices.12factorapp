package net.wasdev.twelvefactorapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class JaxrsHttpReceiver {
	String databaseName = "testdb";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse() throws NullPointerException, IOException {
		String dbFiles = getDatabaseFiles("/_all_dbs");
		String items = getDatabaseFiles("/items/_all_docs");
		return Response.ok("All databases: " + dbFiles + "Items database: " + items).build();
	}
	
	public String getDatabaseFiles(String extension) throws NullPointerException, IOException {
		System.out.println("Getting database files for extension" + extension);
		// Has been configured to match the cloudant database that we own in bluemix.
		String[] credentials = getCredentials();
		String username = credentials[0];
		String password = credentials[1];
		String url = credentials[2];
		String fullUrl = url + extension;
		System.out.println("Found url " + fullUrl);
		
		String usernameAndPassword = username + ":" + password;
				
		String authorizationHeaderName = "Authorization";
		String authorizationHeaderValue = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(usernameAndPassword.getBytes());
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(fullUrl);
		Invocation.Builder invoBuild  = target.request(MediaType.APPLICATION_JSON).header(authorizationHeaderName, authorizationHeaderValue);
		Response httpResponse = invoBuild.get();
		String contents = httpResponse.readEntity(String.class);
		httpResponse.close();
		return contents;
		
	}
	
	public String [] getCredentials() throws NullPointerException, IOException {
		String dbUsername = null;
		String dbPassword = null;
		String dbUrl = null;
		// If there is a VCAP_SERVICES use that json object
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		if (VCAP_SERVICES != null) {
			InputStream is = new ByteArrayInputStream(VCAP_SERVICES.getBytes());
			JsonReader reader = Json.createReader(is);
			JsonObject jsonObj = reader.readObject();
			JsonArray services = (JsonArray) jsonObj.get("cloudantNoSQLDB");
			for (Object service : services) {
				JsonObject cloudantService = (JsonObject) service;
				JsonObject credentials = (JsonObject) cloudantService.get("credentials");
				dbUsername = ((JsonString) credentials.get("username")).getString();
				dbPassword = ((JsonString) credentials.get("password")).getString();
				dbUrl = ((JsonString) credentials.get("url")).getString();
				System.out.println(dbUsername + " " + dbPassword + " " + dbUrl);
			}
		} else {
			dbUsername = System.getenv("dbUsername");
			dbPassword = System.getenv("dbPassword");
			dbUrl = System.getenv("dbUrl");
		}
		String[] dbCredentials = {dbUsername, dbPassword, dbUrl};
		return dbCredentials;
	}
}
