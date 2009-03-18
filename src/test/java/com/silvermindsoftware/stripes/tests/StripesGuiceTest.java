package com.silvermindsoftware.stripes.tests;

import com.silvermindsoftware.stripes.app.actions.TestAction;
import static com.silvermindsoftware.stripes.app.actions.TestAction.FETCH_LIST_RESOLUTION;
import static com.silvermindsoftware.stripes.app.extensions.TestActionBeanContext.TEST_USER_ID;
import static com.silvermindsoftware.stripes.app.domain.TestWidget.DEFAULT_WIDGET_NAME;
import com.silvermindsoftware.stripes.integration.guice.GuiceContextListener;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletContextEvent;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class StripesGuiceTest {

    protected static MockServletContext mockServletContext;
    protected static GuiceContextListener contextListener;

    @BeforeClass
    public static void setUp() {
        mockServletContext = new MockServletContext("web");

        mockServletContext.addInitParameter("Guice.Modules", "com.silvermindsoftware.stripes.guice.TestModule");
        mockServletContext.addInitParameter("GuiceInjectorFactory.Class", "com.silvermindsoftware.stripes.guice.TestGuiceInjectorFactory");
        ServletContextEvent servletContextEvent = new ServletContextEvent(mockServletContext);

        contextListener = new GuiceContextListener();
        contextListener.contextInitialized(servletContextEvent);

        Map<String, String> params = new HashMap<String, String>();
        params.put("ActionResolver.Packages", "com.silvermindsoftware.stripes.app.actions");
        params.put("Extension.Packages", "com.silvermindsoftware.stripes.app.extensions");
        params.put("ActionResolver.Class", "com.silvermindsoftware.stripes.action.GuiceActionResolver");
        params.put("Interceptor.Classes", "com.silvermindsoftware.stripes.integration.guice.GuiceInterceptor");
        mockServletContext.addFilter(StripesFilter.class, "Stripes Filter", params);
        mockServletContext.setServlet(DispatcherServlet.class, "StripesDispatcher", null);
    }

    @Test
    public void testBasic() throws Exception {
        MockRoundtrip mockRoundtrip = new MockRoundtrip(mockServletContext, TestAction.class);
        mockRoundtrip.execute();

        TestAction testAction = mockRoundtrip.getActionBean(TestAction.class);
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
