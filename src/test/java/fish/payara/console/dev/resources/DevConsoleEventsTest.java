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
public class DevConsoleEventsTest extends DevConsoleDeployment {

    @Test
    public void observers_schema_is_valid() {
        JsonArray observers = getJsonArray("/dev/observers");

        if (!observers.isEmpty()) {
            JsonObject o = observers.getJsonObject(0);
            assertTrue(o.containsKey("eventTypeName"));
            assertTrue(o.containsKey("beanClass"));
        }
    }

    @Test
    public void events_schema_is_valid() {
        JsonArray events = getJsonArray("/dev/events");

        if (!events.isEmpty()) {
            JsonObject e = events.getJsonObject(0);
            assertTrue(e.containsKey("eventType"));
            assertTrue(e.containsKey("timestamp"));
        }
    }
}
