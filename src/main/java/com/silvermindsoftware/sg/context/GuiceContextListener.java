package com.silvermindsoftware.sg.context;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.silvermindsoftware.sg.guice.GuiceInjectorFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static com.silvermindsoftware.sg.utils.Constants.DEFAULT_GUICE_INJECTOR_FACTORY_CLASS_NAME;
import static com.silvermindsoftware.sg.utils.Constants.GUICE_INJECTOR_FACTORY_CLASS_NAME;
import static com.silvermindsoftware.sg.utils.Helpers.createClass;
import static com.silvermindsoftware.sg.utils.Helpers.isEmptyOrNull;

/**
 * <p>
 * The following can be added to the web.xml in order to use the
 * {@link GuiceContextListener
 * GuiceContextListener}. Just replace the GuiceModules param-value with a
 * comma-delimited list of Modules you want to initialize.
 * </p>
 * <p/>
 * <code>
 * &lt;listener&gt;<br/>
 * &nbsp;&nbsp;&lt;listener-class&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;com.silvermindsoftware.sg.context.GuiceContextListener<br/>
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
 * {@link GuiceInjectorFactory
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
public class GuiceContextListener extends GuiceServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiceContextListener.class);
    @NotNull
    private ServletContext theServletContext;

    @Override
    public void contextInitialized(@NotNull ServletContextEvent aServletContextEvent) {
        theServletContext = aServletContextEvent.getServletContext();
        super.contextInitialized(aServletContextEvent);
    }

    @Override
    @NotNull
    protected Injector getInjector() {
        LOGGER.debug("Creating Injector");

        final String myClassname = getGuiceInjectorFactoryClassName(theServletContext);
        final GuiceInjectorFactory myFactory = createClass(myClassname, GuiceInjectorFactory.class);

        return myFactory.getInjector(theServletContext);
    }

    @NotNull
    private String getGuiceInjectorFactoryClassName(@NotNull ServletContext aServletcontext) {
        final String myClassname = aServletcontext.getInitParameter(GUICE_INJECTOR_FACTORY_CLASS_NAME);
        return isEmptyOrNull(myClassname) ? DEFAULT_GUICE_INJECTOR_FACTORY_CLASS_NAME : myClassname;
    }
}
