package fish.payara.console.dev.resources;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class DevConsoleMetadataTest extends DevConsoleDeployment {

    @Test
    public void metadata_contains_expected_counters() {
        JsonObject meta = getJsonObject("/dev/metadata");

        assertTrue(meta.containsKey("beanCount"));
        assertTrue(meta.containsKey("producerCount"));
        assertTrue(meta.containsKey("interceptorCount"));
        assertTrue(meta.containsKey("restResourceCount"));

        assertEquals(JsonValue.ValueType.NUMBER,
                meta.get("beanCount").getValueType());
    }

}
