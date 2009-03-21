package com.silvermindsoftware.stripes.tests;

import com.silvermindsoftware.stripes.integration.guice.CreateClassException;
import com.silvermindsoftware.stripes.integration.guice.GuiceContextListener;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.*;
import net.sourceforge.stripes.mock.MockServletContext;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import javax.servlet.ServletContextEvent;
import java.text.MessageFormat;

public class StripesGuiceFailTest {

    @Test
    public void testFailOnGuiceModuleName() {

        MockServletContext mockServletContext = new MockServletContext("web");
        mockServletContext.addInitParameter(GUICE_MODULES_PARAM, "com.silvermindsoftware.stripes.guice.IDontExistModule");
        mockServletContext.addInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME, "com.silvermindsoftware.stripes.guice.TestGuiceInjectorFactory");

        ServletContextEvent servletContextEvent = new ServletContextEvent(mockServletContext);


        boolean successfulFailure = false;
        String expectedException = "Exception thrown while instantiating Modules in";
        try {
            GuiceContextListener contextListener = new GuiceContextListener();
            contextListener.contextInitialized(servletContextEvent);
        } catch (Exception e) {
            successfulFailure = e instanceof RuntimeException && e.getMessage().contains(expectedException);
            assertTrue(
                    MessageFormat.format("contextListener.contextInitialized(servletContextEvent) call should have failed with an exception of type {0}",
                            CreateClassException.class.getName()), successfulFailure);

        }
        assertTrue(MessageFormat.format("Excpected Exception was not throw. Message should have contained ''{0}''", expectedException), successfulFailure);

    }


    @Test
    public void testFailOnGuiceInjectorFactory() {

        MockServletContext mockServletContext = new MockServletContext("web");
        mockServletContext.addInitParameter(GUICE_MODULES_PARAM, "com.silvermindsoftware.stripes.guice.TestModule");
        mockServletContext.addInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME, "com.silvermindsoftware.stripes.guice.IDontExistGuiceInjectorFactory");

        ServletContextEvent servletContextEvent = new ServletContextEvent(mockServletContext);


        boolean successfulFailure = false;
        String expectedException = "Exception thrown while initializing Injector in";
        try {
            GuiceContextListener contextListener = new GuiceContextListener();
            contextListener.contextInitialized(servletContextEvent);
        } catch (Exception e) {
            successfulFailure = e instanceof RuntimeException && e.getMessage().contains(expectedException);
            assertTrue(
                    MessageFormat.format("contextListener.contextInitialized(servletContextEvent) call should have failed with an exception of type {0}",
                            CreateClassException.class.getName()), successfulFailure);

        }
        assertTrue(MessageFormat.format("Excpected Exception was not throw. Message should have contained ''{0}''", expectedException), successfulFailure);

    }

}