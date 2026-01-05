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
    public void rest_method_full_endpoint_returns_records_array() {
        JsonArray methods = getJsonArray("/dev/rest-methods");

        if (!methods.isEmpty()) {
            JsonObject m = methods.getJsonObject(0);

            String path = m.getString("methodSignature", null);
            if (path != null) {
                JsonObject full = getJsonObject("/dev/rest-methods/" + path);

                assertTrue(full.containsKey("records"));
                assertTrue(full.get("records").getValueType()
                        == jakarta.json.JsonValue.ValueType.ARRAY);
            }
        }
    }

}
