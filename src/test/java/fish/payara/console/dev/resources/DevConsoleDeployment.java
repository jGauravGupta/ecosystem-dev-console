package fish.payara.console.dev.resources;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.io.File;

public class DevConsoleDeployment extends AbstractDevConsoleArquillianTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(DevConsoleResource.class)
                .addPackages(true, "fish.payara.console.dev")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
    }
}
