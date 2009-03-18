package com.silvermindsoftware.stripes.guice;

import com.silvermindsoftware.stripes.integration.guice.GuiceInjectorFactory;
import com.silvermindsoftware.stripes.integration.guice.DefaultGuiceInjectorFactory;
import com.google.inject.Injector;
import com.google.inject.Guice;

import javax.servlet.ServletContext;

public class TestGuiceInjectorFactory extends DefaultGuiceInjectorFactory implements GuiceInjectorFactory {

    public Injector getInjector(ServletContext servletContext) {
        Injector injector = super.getInjector(servletContext);
        InjectorHelper.init(injector);
        return injector;
    }
    
}
