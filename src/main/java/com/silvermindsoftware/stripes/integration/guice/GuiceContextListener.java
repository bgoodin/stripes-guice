package com.silvermindsoftware.stripes.integration.guice;

import com.google.inject.Injector;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.DEFAULT_GUICE_INJECTOR_FACTORY_CLASS_NAME;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.GUICE_INJECTOR_FACTORY_CLASS_NAME;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.MessageFormat;

/**
 * <p>
 * The following can be added to the web.xml in order to use the
 * {@link com.silvermindsoftware.stripes.integration.guice.GuiceContextListener
 * GuiceContextListener}. Just replace the GuiceModules param-value with a
 * comma-delimited list of Modules you want to initialize.
 * </p>
 * <p/>
 * <code>
 * &lt;listener&gt;<br/>
 * &nbsp;&nbsp;&lt;listener-class&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;com.silvermindsoftware.stripes.integration.guice.GuiceContextListener<br/>
 * &nbsp;&nbsp;&lt;/listener-class&gt;<br/>
 * &lt;/listener&gt;<br/>
 * </code>
 * <p/>
 * <br/>
 * <p/>
 * <code>
 * &lt;context-param&gt;<br/>
 * &nbsp;&nbsp;&lt;param-name&gt;Guice.Modules&lt;/param-name&gt;<br/>
 * &nbsp;&nbsp;&lt;param-value&gt;fully-qualified-module-class-name[[,fully-qualified-module-class-name]... ]&lt;/param-value&gt;<br/>
 * &lt;/context-param&gt;<br/>
 * </code>
 * <p/>
 * <p>
 * Alternate functionality can be provided for creating the Injector. Simply
 * implement the
 * {@link com.silvermindsoftware.stripes.integration.guice.GuiceInjectorFactory
 * DefaultGuiceInjectorFactory} and specify it in an context-param:
 * </p>
 * <p/>
 * <code>
 * &lt;context-param&gt;<br/>
 * &nbsp;&nbsp;&lt;param-name&gt;GuiceInjectorFactory.Class&lt;/param-name&gt;<br/>
 * &nbsp;&nbsp;&lt;param-value&gt;fully-qualified-class-name&lt;/param-value&gt;<br/>
 * &lt;/context-param&gt;<br/>
 * </code>
 */
public class GuiceContextListener implements ServletContextListener {

    private static final Log log = LogFactory.getLog(GuiceContextListener.class);
    protected static Injector injector;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("Initializing {0}", GuiceContextListener.class.getName()));
        }
        initInjector(servletContextEvent.getServletContext());
    }

    /**
     * @param servletContext
     */
    protected synchronized void initInjector(ServletContext servletContext) {
        String className = servletContext.getInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME);
        if (className == null)
            className = DEFAULT_GUICE_INJECTOR_FACTORY_CLASS_NAME;
        else {
            // Make sure the className is trimmed for the check and the usage
            // later on
            className = className.trim();
            if (className.length() == 0)
                className = DEFAULT_GUICE_INJECTOR_FACTORY_CLASS_NAME;
        }
        // Maybe also this way, but I'm not sure if the string will be null or
        // empty if no init parameter is supplied
        // className = className == null ?
        // DEFAULT_GUICE_INJECTOR_FACTORY_CLASS_NAME : className.trim();
        try {
            if (log.isDebugEnabled())
                log.debug(MessageFormat.format("Instantiating {0} of type {1}", GuiceInjectorFactory.class.getName(),
                        className));
            GuiceInjectorFactory factory = GuiceUtils.createClass(className, GuiceInjectorFactory.class);
            injector = factory.getInjector(servletContext);
        } catch (CreateClassException e) {
            String message = MessageFormat.format("Exception thrown while initializing Injector in {0}: {1}", GuiceContextListener.class.getName(), e.getMessage());
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    /**
     * access to injector that is not tied to a context
     *
     * @return
     */
    public static Injector getInjector() {
        return injector;
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        injector = null;
    }
}
