package com.silvermindsoftware.stripes.tests;

import com.silvermindsoftware.stripes.integration.guice.GuiceContextListener;
import net.sourceforge.stripes.mock.MockServletContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.servlet.ServletContextEvent;

import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.GUICE_INJECTOR_FACTORY_CLASS_NAME;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.GUICE_MODULES_PARAM;

public class StripesGuiceFailTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFailOnGuiceModuleName() {
        final String expectedException = "Exception thrown while instantiating Modules in";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(expectedException);

        final MockServletContext mockServletContext = new MockServletContext("web");
        mockServletContext.addInitParameter(GUICE_MODULES_PARAM,
            "com.silvermindsoftware.stripes.guice.IDontExistModule");
        mockServletContext.addInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME,
            "com.silvermindsoftware.stripes.guice.TestGuiceInjectorFactory");

        final ServletContextEvent servletContextEvent = new ServletContextEvent(mockServletContext);
        final GuiceContextListener contextListener = new GuiceContextListener();
        contextListener.contextInitialized(servletContextEvent);
    }


    @Test
    public void testFailOnGuiceInjectorFactory() {
        final String expectedException = "Exception thrown while initializing Injector in";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(expectedException);

        final MockServletContext mockServletContext = new MockServletContext("web");
        mockServletContext.addInitParameter(GUICE_MODULES_PARAM, "com.silvermindsoftware.stripes.guice.TestModule");
        mockServletContext.addInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME,
            "com.silvermindsoftware.stripes.guice.IDontExistGuiceInjectorFactory");

        final ServletContextEvent servletContextEvent = new ServletContextEvent(mockServletContext);
        final GuiceContextListener contextListener = new GuiceContextListener();
        contextListener.contextInitialized(servletContextEvent);
    }
}