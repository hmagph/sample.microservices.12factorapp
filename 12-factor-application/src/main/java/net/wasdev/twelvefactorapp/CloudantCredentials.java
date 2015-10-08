package net.wasdev.twelvefactorapp;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

public class CloudantCredentials {
	
	
	private String username = System.getenv("dbUsername");
	private String password = System.getenv("dbPassword");
	private String url = System.getenv("dbUrl");
	
	public CloudantCredentials() throws Exception {
		
		boolean allVariablesFound = false;
		if (username != null && password!= null && url != null) {
			allVariablesFound = true;
		} else {
			parseVcapServices();
			if (username != null && password!= null && url != null) {
				allVariablesFound = true;
			}
		}
		
		if (!allVariablesFound) {
			throw new Exception("Database cannot be accessed at this time, something is null.");
		}
	}

	private void parseVcapServices() {
		String vcapServices = System.getenv("VCAP_SERVICES");
		JsonObject body = Json.createReader(new StringReader(vcapServices)).readObject();
		JsonArray cloudantObjectArray = body.getJsonArray("cloudantNoSQLDB");
		JsonObject cloudantObject = cloudantObjectArray.getJsonObject(0);
		JsonObject cloudantCredentials = cloudantObject.getJsonObject("credentials");
		JsonString cloudantUsername = cloudantCredentials.getJsonString("username");
		username = cloudantUsername.getString();
		JsonString cloudantPassword = cloudantCredentials.getJsonString("password");
		password = cloudantPassword.getString();
		JsonString cloudantUrl = cloudantCredentials.getJsonString("url");
		url = cloudantUrl.getString();
	}

	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	
	
	
}
