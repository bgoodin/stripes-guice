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
 *
 * The following can be added to the web.xml in order to use the default GuiceContextListener.
 * Just replace the GuiceModules param-value with a comma-delimited list of Modules you want
 * to initialize.
 * 
 * &lt;listener&gt;
 * &nbsp;&nbsp;&lt;listener-class&gt;
 * &nbsp;&nbsp;&nbsp;&nbsp;com.silvermindsoftware.stripes.integration.guice.GuiceContextListener
 * &nbsp;&nbsp;&lt;/listener-class&gt;
 * &lt;/listener&gt;
 * 
 * &lt;context-param&gt;
 * &nbsp;&nbsp;&lt;param-name&gt;Guice.Modules&lt;/param-name&gt;
 * &nbsp;&nbsp;&lt;param-value&gt;fully-qualified-module-class-name[[,fully-qualified-module-class-name]... ]&lt;/param-value&gt;
 * &lt;/context-param&gt;
 *
 * Alternate functionality can be provided for creating the Injector. Simply implement the
 * DefaultGuiceInjectoFactory and specify it in an context-param:
 * 
 * &lt;context-param&gt;
 * &nbsp;&nbsp;&lt;param-name&gt;GuiceInjectorFactory.Class&lt;/param-name&gt;
 * &nbsp;&nbsp;&lt;param-value&gt;fully-qualified-class-name&lt;/param-value&gt;
 * &lt;/context-param&gt;
 *
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
