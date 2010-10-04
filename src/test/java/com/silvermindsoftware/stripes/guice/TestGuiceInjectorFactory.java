package com.silvermindsoftware.stripes.guice;

import com.google.inject.Injector;
import com.silvermindsoftware.stripes.integration.guice.DefaultGuiceInjectorFactory;
import com.silvermindsoftware.stripes.integration.guice.GuiceInjectorFactory;

import javax.servlet.ServletContext;

public class TestGuiceInjectorFactory extends DefaultGuiceInjectorFactory implements GuiceInjectorFactory {

    public Injector getInjector(ServletContext servletContext) {
        final Injector injector = super.getInjector(servletContext);
        InjectorHelper.init(injector);
        return injector;
    }

}
