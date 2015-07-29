package net.wasdev.twelvefactorapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.wasdev.twelvefactorapp.ResponseHandler.RequestType;

@Path("/")
public class JaxrsHttpReceiver {
	String defaultDatabaseName = "items";
	
	public String getDefaultDatabaseName() {
		return defaultDatabaseName;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponse() throws NullPointerException, IOException {
		try {
		String dbFiles = getDatabases();
		String items = getDatabaseFiles(defaultDatabaseName);
		return Response.ok("All databases: " + dbFiles + " " + defaultDatabaseName + " database: " + items).build();
		} catch (Exception e) {
			return Response.ok("Response is " + e.getMessage()).build();
		}
	}
	
	public String getDatabases() throws Exception {
		System.out.println("Getting all databases");
		try {
			ResponseHandler responseHandler = new ResponseHandler("/_all_dbs");
			String response = responseHandler.invoke(RequestType.GET);
			return response;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String getDatabaseFiles(String database) throws Exception {
		System.out.println("Getting files for database" + database);
		try {
			ResponseHandler responseHandler = new ResponseHandler("/" + database + "/_all_docs");
			String response = responseHandler.invoke(RequestType.GET);
			return response;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postResponse(String data) throws NullPointerException, IOException {
		String contents = storeData(data);
		return Response.ok("Response is " + contents).build();
	}
	
	public String storeData(String data) throws NullPointerException, IOException {
		System.out.println("Storing data " + data);
		// Convert string to jsonObject
		InputStream is = new ByteArrayInputStream(data.getBytes());
		JsonReader reader = Json.createReader(is);
		JsonObject jsonData = reader.readObject();
		Entity<myObject> ent = Entity.entity(new myObject(jsonData), MediaType.APPLICATION_JSON);
		// Get response
		try {
			ResponseHandler responseHandler = new ResponseHandler("/" + defaultDatabaseName + "/");
			String response = responseHandler.invoke(RequestType.POST, ent);
			return response;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
