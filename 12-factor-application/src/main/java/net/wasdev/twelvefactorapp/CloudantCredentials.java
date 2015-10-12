package net.wasdev.twelvefactorapp;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

public class CloudantCredentials {
	
	private String username;
	private String password;
	private String url;
	
	public CloudantCredentials(String username, String password, String url, String vcapServices) throws Exception {
		boolean allVariablesFound = false;
		if (username != null && password!= null && url != null) {
			this.url = url;
			this.username = username;
			this.password = password;
			allVariablesFound = true;
		} else {
			parseVcapServices(vcapServices);
			if (this.username != null && this.password!= null && this.url != null) {
				allVariablesFound = true;
			}
		}
		
		if (!allVariablesFound) {
			throw new Exception("Database cannot be accessed at this time, something is null.");
		}
	}

	private void parseVcapServices(String vcapServicesEnv) {
		JsonObject vcapServices = Json.createReader(new StringReader(vcapServicesEnv)).readObject();
		JsonArray cloudantObjectArray = vcapServices.getJsonArray("cloudantNoSQLDB");
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
		return url;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
	
	
	
}
