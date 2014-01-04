package com.silvermindsoftware.sg.stripes.tests;

import com.silvermindsoftware.sg.context.GuiceContextListener;
import com.silvermindsoftware.sg.stripes.guice.TestGuiceInjectorFactory;
import com.silvermindsoftware.sg.stripes.guice.TestModule;
import net.sourceforge.stripes.mock.MockServletContext;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.servlet.ServletContextEvent;

import static com.silvermindsoftware.sg.utils.Constants.GUICE_INJECTOR_FACTORY_CLASS_NAME;
import static com.silvermindsoftware.sg.utils.Constants.GUICE_MODULES_PARAM;

public class StripesGuiceFailTest {
    private static final String EXPECTED_EXCEPTION = "Exception when creating class";
    @NotNull
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFailOnGuiceModuleName() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_EXCEPTION);

        final MockServletContext mockServletContext = new MockServletContext("web");
        mockServletContext.addInitParameter(GUICE_MODULES_PARAM,
                "com.silvermindsoftware.sg.stripes.guice.IDontExistModule");
        mockServletContext.addInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME,
                TestGuiceInjectorFactory.class.getName());

        final ServletContextEvent servletContextEvent = new ServletContextEvent(mockServletContext);
        final GuiceContextListener contextListener = new GuiceContextListener();
        contextListener.contextInitialized(servletContextEvent);
    }


    @Test
    public void testFailOnGuiceInjectorFactory() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(EXPECTED_EXCEPTION);

        final MockServletContext mockServletContext = new MockServletContext("web");
        mockServletContext.addInitParameter(GUICE_MODULES_PARAM, TestModule.class.getName());
        mockServletContext.addInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME,
                "com.silvermindsoftware.sg.stripes.guice.IDontExistGuiceInjectorFactory");

        final ServletContextEvent servletContextEvent = new ServletContextEvent(mockServletContext);
        final GuiceContextListener contextListener = new GuiceContextListener();
        contextListener.contextInitialized(servletContextEvent);
    }
}