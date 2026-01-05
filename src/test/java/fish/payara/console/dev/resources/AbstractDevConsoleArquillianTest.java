package fish.payara.console.dev.resources;

import org.jboss.arquillian.test.api.ArquillianResource;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public abstract class AbstractDevConsoleArquillianTest {

    @ArquillianResource
    protected URL deploymentUrl;

    protected JsonArray getJsonArray(String path) {
        String json = getJson(path);
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            return reader.readArray();
        }
    }

    protected JsonObject getJsonObject(String path) {
        String json = getJson(path);
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            return reader.readObject();
        }
    }

    private String getJson(String path) {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target(deploymentUrl.toString() + "resources" + path)
                .request(MediaType.APPLICATION_JSON)
                .get();

        try {
            assertEquals(200, response.getStatus());
            return response.readEntity(String.class);
        } finally {
            response.close();
            client.close();
        }
    }
}
