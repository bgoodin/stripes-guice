package com.silvermindsoftware.stripes.integration.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.GUICE_MODULES_PARAM;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.splitClasses;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import java.text.MessageFormat;

public class DefaultGuiceInjectorFactory implements GuiceInjectorFactory {

    protected static final Log log = LogFactory.getLog(DefaultGuiceInjectorFactory.class);

    public Injector getInjector(ServletContext servletContext) {
        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("Creating Injector with {0}.", DefaultGuiceInjectorFactory.class.getName()));
        }
        String guiceModules = servletContext.getInitParameter(GUICE_MODULES_PARAM);
        Module[] modules = null;

        if (guiceModules != null && guiceModules.trim().length() > 0) {

            String[] classNames = splitClasses(guiceModules);
            if (classNames.length == 0) {
                throw new RuntimeException(GUICE_MODULES_PARAM + " was not specified in:\n" +
                        "<context-param>\n" +
                        "\t<param-name>" + GUICE_MODULES_PARAM + "</param-name>\n" +
                        "\t<param-value>full-qualified-class-name[,full-qualified-class-name]</param-value>\n" +
                        "</context-param>");
            }

            modules = new Module[classNames.length];
            int x = 0;
            for (String className : classNames) {
                try {
                    if (log.isDebugEnabled()) {
                        log.debug(MessageFormat.format("Instantiating Module of type {0}.", className));
                    }
                    modules[x] = (Module) Class.forName(className).newInstance();
                } catch (ClassNotFoundException e) {
                    log.error(
                            MessageFormat.format(
                                    "{0} thrown during Module instantiation of {1} with message {2}",
                                    ClassNotFoundException.class.getName(),
                                    className, e.getMessage()), e);
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    log.error(
                            MessageFormat.format(
                                    "{0} thrown during Module instantiation of {1} with message {2}",
                                    IllegalAccessException.class.getName(),
                                    className, e.getMessage()), e);
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    log.error(
                            MessageFormat.format(
                                    "{0} thrown during Module instantiation of {1} with message {2}",
                                    InstantiationException.class.getName(),
                                    className, e.getMessage()), e);
                    throw new RuntimeException(e);
                }
                x++;
            }

        }

        return Guice.createInjector(modules);
    }

}
