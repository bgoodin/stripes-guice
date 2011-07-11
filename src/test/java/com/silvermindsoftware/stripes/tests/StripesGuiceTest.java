package com.silvermindsoftware.stripes.tests;

import com.google.inject.servlet.GuiceFilter;
import com.silvermindsoftware.sg.config.GuiceRuntimeConfiguration;
import com.silvermindsoftware.sg.context.GuiceContextListener;
import com.silvermindsoftware.sg.controller.GuiceActionBeanContextFactory;
import com.silvermindsoftware.sg.controller.GuiceActionResolver;
import com.silvermindsoftware.sg.stripes.app.actions.TestAction;
import com.silvermindsoftware.sg.stripes.app.extensions.TestActionBeanContext;
import com.silvermindsoftware.sg.stripes.app.interceptors.TestInterceptor;
import com.silvermindsoftware.sg.stripes.guice.TestGuiceInjectorFactory;
import com.silvermindsoftware.sg.stripes.guice.TestModule;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletContextEvent;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.silvermindsoftware.sg.stripes.app.actions.TestAction.FETCH_LIST_RESOLUTION;
import static com.silvermindsoftware.sg.stripes.app.domain.TestWidget.DEFAULT_WIDGET_NAME;
import static com.silvermindsoftware.sg.stripes.app.extensions.TestActionBeanContext.TEST_USER_ID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StripesGuiceTest {
    @NotNull private static MockServletContext mockServletContext;

    @BeforeClass
    public static void setUp() {
        final Map<String, String> params = new HashMap<String, String>();

        params.put("ActionResolver.Packages", TestAction.class.getPackage().getName());
        params.put("Extension.Packages", TestActionBeanContext.class.getPackage().getName());
        params.put("Interceptor.Classes", TestInterceptor.class.getName());

        params.put("ActionResolver.Class", GuiceActionResolver.class.getName());
        params.put("ActionBeanContextFactory.Class", GuiceActionBeanContextFactory.class.getName());
        params.put("Configuration.Class", GuiceRuntimeConfiguration.class.getName());

        mockServletContext = new MockServletContext("web");
        mockServletContext.addInitParameter("Guice.Modules", TestModule.class.getName());
        mockServletContext.addInitParameter("GuiceInjectorFactory.Class", TestGuiceInjectorFactory.class.getName());

        final ServletContextEvent servletContextEvent = new ServletContextEvent(mockServletContext);
        final GuiceContextListener myContextListener = new GuiceContextListener();
        myContextListener.contextInitialized(servletContextEvent);

        mockServletContext.addFilter(GuiceFilter.class, "Guice Filter", params);
        mockServletContext.addFilter(StripesFilter.class, "Stripes Filter", params);
        mockServletContext.setServlet(DispatcherServlet.class, "StripesDispatcher", null);
    }

    @Test
    public void testBasicWithRuntimeInterceptorInjection() throws Exception {
        final MockRoundtrip mockRoundtrip = new MockRoundtrip(mockServletContext, TestAction.class);
        mockRoundtrip.execute();

        final TestAction testAction = mockRoundtrip.getActionBean(TestAction.class);
        assertNotNull("TestUser is null", testAction.getTestUser());
        assertNotNull("TestUser id is null and should have a value.", testAction.getTestUser().getId());
        assertTrue("TestUser has wrong id", testAction.getTestUser().getId().equals(TEST_USER_ID));
        assertNotNull("TestWidget is not null", testAction.getTestWidget());
        assertTrue(MessageFormat.format("TestWidget name should be {0} but is {1}",
            DEFAULT_WIDGET_NAME, testAction.getTestWidget().getWidgetName()),
            testAction.getTestWidget().getWidgetName().equals(DEFAULT_WIDGET_NAME));
        assertTrue(
            MessageFormat.format("Expected destination of {0} but received {1} instead.",
                FETCH_LIST_RESOLUTION, mockRoundtrip.getDestination()),
            mockRoundtrip.getDestination().equals(FETCH_LIST_RESOLUTION));
    }
}
