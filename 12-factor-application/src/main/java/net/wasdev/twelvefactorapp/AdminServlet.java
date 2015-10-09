package net.wasdev.twelvefactorapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@WebServlet("/admin/stats")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String statsExtension = "/IBMJMXConnectorREST/mbeans/WebSphere%3Aname%3D12-factor-application.net.wasdev.twelvefactorapp.JaxrsApplication%2Ctype%3DServletStats/attributes?attribute=RequestCountDetails";

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        String authorizationHeaderName = "Authorization";
        String authorizationHeaderValue = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary("kate:password".getBytes("UTF-8"));
        String url = "";
        String requestURI = httpRequest.getRequestURI().toString();
        String requestUrl = httpRequest.getRequestURL().toString();
        String subUrl = requestUrl.substring(0, requestUrl.indexOf(requestURI));
        url = subUrl + statsExtension;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invoBuild = target.request(MediaType.APPLICATION_JSON).header(authorizationHeaderName, authorizationHeaderValue);
        Response response = invoBuild.get();
        String resp = response.readEntity(String.class);
        response.close();
//		String stats = parse(resp);
        String stats = resp;
        PrintWriter out = httpResponse.getWriter();
        out.println("Stats: " + stats + " Status: " + httpResponse.getStatus());
    }

    // Not quite working yet - getting a decoding error
    private String parse(String stats) throws IOException {
        // Convert string to jsonObject
        InputStream is = new ByteArrayInputStream(stats.getBytes("UTF-8"));
        JsonReader reader = Json.createReader(is);
        String output = "";
        try {
            JsonArray jsonArray = reader.readArray();
            JsonObject jsonObject = jsonArray.getJsonObject(0);
            JsonObject topLevelValue = (JsonObject) jsonObject.get("value");
            JsonObject value = (JsonObject) topLevelValue.get("value");
            JsonValue currentValue = value.get("currentValue");
            JsonValue desc = value.get("description");
            output = "Stats:" + desc.toString() + ": " + currentValue.toString();
        } catch (JsonException e) {
            reader.close();
            is.close();
            if (e.getMessage().equals("Cannot read JSON array, found JSON object")) {
                output = "MXBean not created yet, the application must be accessed at least "
                         + "once to get statistics";
            } else {
                output = "A JSON Exception occurred: " + e.getMessage();
            }
        }
        reader.close();
        is.close();
        return output;
    }

}
