package com.silvermindsoftware.stripes.integration.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DefaultGuiceInjectorFactory implements GuiceInjectorFactory {
    protected static final Log log = LogFactory.getLog(DefaultGuiceInjectorFactory.class);

    public Injector getInjector(ServletContext servletContext) {
        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("Creating Injector with {0}.", DefaultGuiceInjectorFactory.class.getName()));
        }
        String guiceModules = servletContext.getInitParameter(GUICE_MODULES_PARAM);
        List<Module> modules = new ArrayList<Module>();
        if (guiceModules != null && guiceModules.trim().length() > 0) {
            String[] classNames = splitClasses(guiceModules);
            if (classNames.length == 0) {
                throw new RuntimeException(GUICE_MODULES_PARAM + " was not specified in:\n" + "<context-param>\n"
                        + "\t<param-name>" + GUICE_MODULES_PARAM + "</param-name>\n"
                        + "\t<param-value>full-qualified-class-name[,full-qualified-class-name]</param-value>\n"
                        + "</context-param>");
            }

            try {
                for (String className : classNames)
                    modules.add(createClass(className, Module.class));
            } catch (CreateClassException e) {
                String message = MessageFormat.format(
                        "Exception thrown while instantiating Modules in {0}.getInjector(): {1}", 
                        DefaultGuiceInjectorFactory.class.getName(), e.getMessage());
                log.error(message, e);
                throw new RuntimeException(message, e);
            }
        }

        return Guice.createInjector(modules);
    }
}
