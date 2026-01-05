package fish.payara.console.dev.resources;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@RunAsClient
public class DevConsoleRestTest extends DevConsoleDeployment {

    @Test
    public void rest_resources_schema_is_valid() {
        JsonArray resources = getJsonArray("/dev/rest-resources");

        if (!resources.isEmpty()) {
            JsonObject r = resources.getJsonObject(0);
            assertTrue(r.containsKey("className"));
            assertTrue(r.containsKey("path"));
        }
    }

    @Test
    public void injection_points_schema_is_valid() {
        JsonArray ips = getJsonArray("/dev/injection-points");

        if (!ips.isEmpty()) {
            JsonObject ip = ips.getJsonObject(0);
            assertTrue(ip.containsKey("declaringBean"));
            assertTrue(ip.containsKey("status"));
        }
    }
}
