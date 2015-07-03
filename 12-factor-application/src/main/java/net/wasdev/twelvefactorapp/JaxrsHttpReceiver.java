package net.wasdev.twelvefactorapp;

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
	public Response getResponse() {
		String response = "Hello this is a response ";
		String dbFiles = getDatabaseFiles();
		return Response.ok(response + dbFiles).build();
	}
	
	public String getDatabaseFiles() {
		Client client = ClientBuilder.newClient();
		String dbUrl = System.getenv("dbUrl");
		String url = dbUrl;
		WebTarget target = client.target(url);
		Invocation.Builder invoBuild  = target.request(MediaType.APPLICATION_JSON);
		Response httpResponse = invoBuild.get();
		String contents = httpResponse.readEntity(String.class);
		httpResponse.close();
		return contents + " url was " + url;
		
	}

}
