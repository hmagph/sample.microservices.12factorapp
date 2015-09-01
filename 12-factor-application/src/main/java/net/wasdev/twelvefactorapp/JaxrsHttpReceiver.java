package net.wasdev.twelvefactorapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
			return Response.ok(dbFiles).build();
		} catch (Exception e) {
			JsonObject exception = Json.createObjectBuilder().add("Exception", e.getMessage()).build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception).build();
		}
	}
	
	@Path("/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDatabaseResponse(@PathParam("name") String name) {
		try {
			String database = getDatabaseFiles(name);
			return Response.ok(database).build();
		} catch (Exception e) {
			JsonObject exception = Json.createObjectBuilder().add("Exception", e.getMessage()).build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception).build();
		}
		
	}
	
	public String getDatabases() throws Exception {
		System.out.println("Getting all databases");
		ResponseHandler responseHandler = new ResponseHandler("/_all_dbs");
		String response = responseHandler.invoke(RequestType.GET);
		return response;
	}
	
	public String getDatabaseFiles(String database) throws Exception {
		System.out.println("Getting files for database" + database);
		// An example of how to use java.util.logging
		Logger myLogger = Logger.getLogger("net.wasdev.12factorapp.JaxrsHttpReceiver.getDatabaseFiles");
		myLogger.log(Level.INFO, "Extra logging as an example");
		ResponseHandler responseHandler = new ResponseHandler("/" + database + "/_all_docs");
		String response = responseHandler.invoke(RequestType.GET);
		return response;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postResponse(String data) throws NullPointerException, IOException {
		try {
			String contents = storeData(data);
			return Response.ok(contents).build();
		} catch (Exception e) {
			JsonObject exception = Json.createObjectBuilder().add("Exception", e.getMessage()).build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception).build();
		}
	}
	
	@Path("/{name}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postDataResponse(String data, @PathParam("name") String name) {
		try {
			String contents = storeData(data, name);
			return Response.ok(contents).build();
		} catch (Exception e) {
			JsonObject exception = Json.createObjectBuilder().add("Exception", e.getMessage()).build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception).build();
		}
	}
	
	public String storeData(String data) throws NullPointerException, IOException, Exception {
		return storeData(data, defaultDatabaseName);
	}
	
	public String storeData(String data, String database) throws Exception {
		System.out.println("Storing data " + data);
		// Convert string to jsonObject
		InputStream is = new ByteArrayInputStream(data.getBytes());
		JsonReader reader = Json.createReader(is);
		JsonObject jsonData = reader.readObject();
		Entity<myObject> ent = Entity.entity(new myObject(jsonData), MediaType.APPLICATION_JSON);
		// Get response
		ResponseHandler responseHandler = new ResponseHandler("/" + database + "/");
		String response = responseHandler.invoke(RequestType.POST, ent);
		return response;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response putResponse(String databaseName) throws NullPointerException, IOException {
		System.out.println("Creating database called " + databaseName);
		String response;
		try {
		ResponseHandler responseHandler = new ResponseHandler("/" + databaseName);
		response = responseHandler.invoke(RequestType.PUT);
		return Response.ok("Created database " + response).build();
		} catch (Exception e) {
			JsonObject exception = Json.createObjectBuilder().add("Exception", e.getMessage()).build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception).build();
		}
	}
	
	@Path("/{name}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteResponse(@PathParam("name") String name) {
		System.out.println("Deleting database called " + name);
		String response;
		try {
			ResponseHandler responseHandler = new ResponseHandler("/" + name);
			response = responseHandler.invoke(RequestType.DELETE);
			return Response.ok("Deleted database " + response).build();
		} catch (Exception e) {
			JsonObject exception = Json.createObjectBuilder().add("Exception", e.getMessage()).build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception).build();
		}
		
	}
	
}
