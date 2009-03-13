package com.silvermindsoftware.stripes.integration.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * <p>
 * The following can be added to the web.xml in order to use the {@link com.silvermindsoftware.stripes.integration.guice.GuiceContextListener GuiceContextListener}.
 * Just replace the GuiceModules param-value with a comma-delimited list of Modules you want
 * to initialize.
 * </p>
 *
 * <code>
 * &lt;listener&gt;<br/>
 * &nbsp;&nbsp;&lt;listener-class&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;com.silvermindsoftware.stripes.integration.guice.GuiceContextListener<br/>
 * &nbsp;&nbsp;&lt;/listener-class&gt;<br/>
 * &lt;/listener&gt;<br/>
 * </code>
 *
 * <br/>
 *
 * <code>
 * &lt;context-param&gt;<br/>
 * &nbsp;&nbsp;&lt;param-name&gt;Guice.Modules&lt;/param-name&gt;<br/>
 * &nbsp;&nbsp;&lt;param-value&gt;fully-qualified-module-class-name[[,fully-qualified-module-class-name]... ]&lt;/param-value&gt;<br/>
 * &lt;/context-param&gt;<br/>
 * </code>
 *
 * <p>
 * Alternate functionality can be provided for creating the Injector. Simply implement the
 * {@link com.silvermindsoftware.stripes.integration.guice.GuiceInjectorFactory DefaultGuiceInjectorFactory} and specify it in an context-param:
 * </p>
 *
 * <code>
 * &lt;context-param&gt;<br/>
 * &nbsp;&nbsp;&lt;param-name&gt;GuiceInjectorFactory.Class&lt;/param-name&gt;<br/>
 * &nbsp;&nbsp;&lt;param-value&gt;fully-qualified-class-name&lt;/param-value&gt;<br/>
 * &lt;/context-param&gt;<br/>
 * </code>
 */
public class GuiceContextListener implements ServletContextListener {

    protected static final Set<Injector> inejectorSet =
            new HashSet<Injector>();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initInjector(servletContextEvent.getServletContext());
    }

    /**
     *
     * @param servletContext
     */
    protected void initInjector(ServletContext servletContext) {
        String className = servletContext.getInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME);
        if(className == null || className.trim().length() == 0) {
            className = DEFAULT_GUICE_INJECTOR_FACTORY_CLASS_NAME;
        }

        try {
            
            GuiceInjectorFactory factory =
                    (GuiceInjectorFactory)Class.forName(className).newInstance();

            GuiceContextListener.inejectorSet.add(factory.getInjector(servletContext));

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * access to injector that is not tied to a context
     *
     * @return
     */
    public static Injector getInjector() {
        return inejectorSet.iterator().next();
    }


    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        inejectorSet.clear();
    }
}
