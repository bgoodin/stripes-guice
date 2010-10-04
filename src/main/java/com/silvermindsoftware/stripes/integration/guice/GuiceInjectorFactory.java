package com.silvermindsoftware.stripes.integration.guice;

import com.google.inject.Injector;

import javax.servlet.ServletContext;

public interface GuiceInjectorFactory {
    Injector getInjector(ServletContext servletContext);
}
