package fish.payara.console.dev.resources;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class DevConsoleInvocationTest extends DevConsoleDeployment {

    @Test
    public void producers_schema_is_valid() {
        JsonArray producers = getJsonArray("/dev/producers");

        if (!producers.isEmpty()) {
            JsonObject p = producers.getJsonObject(0);

            assertTrue(p.containsKey("className"));
            assertTrue(p.containsKey("producedCount"));
            assertEquals(JsonValue.ValueType.NUMBER,
                    p.get("producedCount").getValueType());
        }
    }

    @Test
    public void interceptors_schema_is_valid() {
        JsonArray interceptors = getJsonArray("/dev/interceptors");

        if (!interceptors.isEmpty()) {
            JsonObject i = interceptors.getJsonObject(0);

            assertTrue(i.containsKey("className"));
            assertTrue(i.containsKey("invokedCount"));
        }
    }

    @Test
    public void decorators_schema_is_valid() {
        JsonArray decorators = getJsonArray("/dev/decorators");

        if (!decorators.isEmpty()) {
            JsonObject d = decorators.getJsonObject(0);

            assertTrue(d.containsKey("className"));
            assertTrue(d.containsKey("invokedCount"));
        }
    }

    @Test
    public void intercepted_classes_have_className() {
        JsonArray intercepted = getJsonArray("/dev/intercepted-classes");

        if (!intercepted.isEmpty()) {
            JsonObject obj = intercepted.getJsonObject(0);
            assertTrue(obj.containsKey("className"));
            assertTrue(obj.containsKey("interceptorBindings"));
        }
    }

    @Test
    public void decorated_classes_have_className() {
        JsonArray decorated = getJsonArray("/dev/decorated-classes");

        if (!decorated.isEmpty()) {
            JsonObject obj = decorated.getJsonObject(0);
            assertTrue(obj.containsKey("className"));
            assertTrue(obj.containsKey("decoratorBindings"));
        }
    }
}
