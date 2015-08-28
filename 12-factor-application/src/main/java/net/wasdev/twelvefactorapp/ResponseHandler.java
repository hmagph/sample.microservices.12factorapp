package net.wasdev.twelvefactorapp;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResponseHandler {

	private Invocation.Builder invoBuild = null;
	
	public enum RequestType {
		GET, POST, PUT, DELETE
	}
	
	public ResponseHandler(String extension) throws Exception {
		String username = System.getenv("dbUsername");
		String password = System.getenv("dbPassword");
		String url = System.getenv("dbUrl");
		
		String[][] credentials = {{username, "username"}, {url, "url"}, {password, "password"}};
		for (String[] credential : credentials) {
			if (credential[0] == null) {
				throw new Exception("Database cannot be accessed at this time, " + credential[1] + " is null.");
			}
		}
		
		String fullUrl = url + extension;
		System.out.println("Found url " + fullUrl);
		
		String usernameAndPassword = username + ":" + password;
				
		String authorizationHeaderName = "Authorization";
		String authorizationHeaderValue = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(usernameAndPassword.getBytes());
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(fullUrl);
		invoBuild  = target.request(MediaType.APPLICATION_JSON).header(authorizationHeaderName, authorizationHeaderValue);
	}
	
	public String invoke(RequestType requestType) throws Exception {
		return invoke(requestType, null);
	}
	
	public String invoke(RequestType requestType, Entity<?> ent) throws Exception {
		if (invoBuild == null) {
			throw new Exception("Database cannot be accessed at this time, invobuild is null");
		}
		Response response = invoBuild.build(requestType.toString(), ent).invoke();
		String resp = response.readEntity(String.class);
		response.close();
		return resp;
	}
	
}
