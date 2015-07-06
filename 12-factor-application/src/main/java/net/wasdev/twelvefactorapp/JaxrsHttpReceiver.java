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
		String dbFiles = getDatabaseFiles("/_all_dbs");
		String items = getDatabaseFiles("/items/_all_docs");
		return Response.ok("All databases: " + dbFiles + "Items database: " + items).build();
	}
	
	public String getDatabaseFiles(String extension) {
		// Has been configured to match the cloudant database that we own in bluemix.
		String username = System.getenv("dbUsername");
		String password = System.getenv("dbPassword");
		String url = System.getenv("dbUrl");
		String fullUrl = url + extension;
		
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
}
