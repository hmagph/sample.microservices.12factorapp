package net.wasdev.twelvefactorapp;

import java.io.IOException;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.wasdev.twelvefactorapp.ResponseHandler.RequestType;

@Path("/admin")
public class JaxrsHttpReceiverAdmin {

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response putResponse(String databaseName) throws NullPointerException, IOException {
		System.out.println("Creating database called " + databaseName);
		String response;
		try {
		ResponseHandler responseHandler = new ResponseHandler("/" + databaseName);
		response = responseHandler.invoke(RequestType.PUT);
		} catch (Exception e) {
			response = e.getMessage();
		}
		return Response.ok("Response is " + response).build();
	}
	
}
