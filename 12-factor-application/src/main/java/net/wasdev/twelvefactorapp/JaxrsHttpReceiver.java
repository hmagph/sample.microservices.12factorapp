package net.wasdev.twelvefactorapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class JaxrsHttpReceiver {
	String databaseName = "items";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse() throws NullPointerException, IOException {
		String dbFiles = getDatabaseFiles("/_all_dbs");
		String items = getDatabaseFiles("/" + databaseName + "/_all_docs");
		if (dbFiles != null && items != null) {
			return Response.ok("All databases: " + dbFiles + "Items database: " + items).build();
		} else {
			return Response.ok("Database credentials not found").build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response putResponse(String databaseName) throws NullPointerException, IOException {
		String contents = createDatabase(databaseName);
		return Response.ok("Response is " + contents).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postResponse(String data) throws NullPointerException, IOException {
		InputStream is = new ByteArrayInputStream(data.getBytes());
		JsonReader reader = Json.createReader(is);
		JsonObject jsonData = reader.readObject();
		String contents = storeData(jsonData);
		return Response.ok("Response is " + contents).build();
	}
	
	public String getDatabaseFiles(String extension) throws NullPointerException, IOException {
		System.out.println("Getting database files for extension" + extension);

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
	
	public String createDatabase(String name) throws NullPointerException, IOException {
		System.out.println("Creating database called " + name);

		String[] credentials = getCredentials();
		if (credentials != null) {
			String username = credentials[0];
			String password = credentials[1];
			String url = credentials[2];
			String fullUrl = url + "/" + name;
			System.out.println("Found url " + fullUrl);		
			String usernameAndPassword = username + ":" + password;
					
			String authorizationHeaderName = "Authorization";
			String authorizationHeaderValue = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(usernameAndPassword.getBytes());		
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(fullUrl);
			Invocation.Builder invoBuild  = target.request(MediaType.APPLICATION_JSON).header(authorizationHeaderName, authorizationHeaderValue);
			Response httpResponse = invoBuild.buildPut(null).invoke();
			String contents = httpResponse.readEntity(String.class);
			httpResponse.close();
			return contents;
		} else {
			return null;
		}
	}
	
	public String storeData(JsonObject data) throws NullPointerException, IOException {
		System.out.println("Storing data " + data);

		String[] credentials = getCredentials();
		String username = credentials[0];
		String password = credentials[1];
		String url = credentials[2];
		String fullUrl = url + "/" + databaseName + "/";
		System.out.println("Found url " + fullUrl);		
		String usernameAndPassword = username + ":" + password;
				
		String authorizationHeaderName = "Authorization";
		String authorizationHeaderValue = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(usernameAndPassword.getBytes());		
			
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(fullUrl);
		Invocation.Builder invoBuild  = target.request(MediaType.APPLICATION_JSON).header(authorizationHeaderName, authorizationHeaderValue);
		Entity<myObject> ent = Entity.entity(new myObject(data), MediaType.APPLICATION_JSON);
		Response httpResponse = invoBuild.buildPost(ent).invoke();
		String contents = httpResponse.readEntity(String.class);
		httpResponse.close();
		return contents;
	}
	
	// This method will either pull the database credentials from VCAP_SERVICES as provided by Bluemix or 
	// it will search for the environment variables dbUsername, dbPassword and dbUrl to get the credentials
	public String [] getCredentials() throws NullPointerException, IOException {
		String dbUsername = null;
		String dbPassword = null;
		String dbUrl = null;
		dbUsername = System.getenv("dbUsername");
		dbPassword = System.getenv("dbPassword");
		dbUrl = System.getenv("dbUrl");
		if (dbUsername != null && dbPassword != null && dbUrl != null) {
			String[] dbCredentials = {dbUsername, dbPassword, dbUrl};
			return dbCredentials;
		} else {
			return null;
		}
	}
}
