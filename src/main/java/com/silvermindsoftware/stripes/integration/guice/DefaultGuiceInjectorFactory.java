package com.silvermindsoftware.stripes.integration.guice;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Guice;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.GUICE_MODULES_PARAM;
import static com.silvermindsoftware.stripes.integration.guice.GuiceUtils.splitClasses;

import javax.servlet.ServletContext;
import java.lang.ref.WeakReference;

public class DefaultGuiceInjectorFactory implements GuiceInjectorFactory {

    public Injector getInjector(ServletContext servletContext) {
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
                    modules[x] = (Module) Class.forName(className).newInstance();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
                x++;
            }
           
        }

        return Guice.createInjector(modules);
    }
    
}
