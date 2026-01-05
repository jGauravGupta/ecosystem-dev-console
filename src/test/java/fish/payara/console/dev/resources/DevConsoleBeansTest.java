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
public class DevConsoleBeansTest extends DevConsoleDeployment {

    @Test
    public void beans_schema_is_valid() {
        JsonArray beans = getJsonArray("/dev/beans");

        if (!beans.isEmpty()) {
            JsonObject bean = beans.getJsonObject(0);
            assertTrue(bean.containsKey("beanType"));
            assertTrue(bean.containsKey("description"));
        }
    }

    @Test
    public void scoped_beans_schema_is_valid() {
        JsonArray beans = getJsonArray("/dev/scoped-beans");

        if (!beans.isEmpty()) {
            JsonObject bean = beans.getJsonObject(0);

            assertTrue(bean.containsKey("scope"));
            assertTrue(bean.containsKey("qualifiers"));
            assertEquals(JsonValue.ValueType.ARRAY,
                    bean.get("qualifiers").getValueType());
            assertTrue(bean.containsKey("alternative"));
        }
    }
}
