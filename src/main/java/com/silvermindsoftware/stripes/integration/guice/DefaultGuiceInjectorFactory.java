package com.silvermindsoftware.stripes.integration.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.GUICE_MODULES_PARAM;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.createClass;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.splitClasses;

public class DefaultGuiceInjectorFactory implements GuiceInjectorFactory {
    protected static final Log log = LogFactory.getLog(DefaultGuiceInjectorFactory.class);

    public Injector getInjector(ServletContext servletContext) {
        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("Creating Injector with {0}.", this.getClass().getName()));
        }

        final String[] moduleNames = getModuleNames(servletContext);
        final List<Module> modules = createModules(moduleNames);

        return Guice.createInjector(modules);
    }

    protected List<Module> createModules(String[] aModuleNames) {
        final List<Module> modules = new ArrayList<Module>();

        try {
            for (String className : aModuleNames) {
                modules.add(createClass(className, Module.class));
            }
        } catch (CreateClassException e) {
            final String message =
                MessageFormat.format(
                    "Exception thrown while instantiating Modules in {0}.getInjector(): {1}",
                    this.getClass().getName(),
                    e.getMessage());

            log.error(message, e);
            throw new RuntimeException(message, e);
        }
        return modules;
    }

    protected String[] getModuleNames(ServletContext servletContext) {
        final String guiceModules = servletContext.getInitParameter(GUICE_MODULES_PARAM);
        return splitClasses(guiceModules);
    }
}
