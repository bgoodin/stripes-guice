package com.silvermindsoftware.sg.stripes.guice;

import com.google.inject.Injector;
import com.silvermindsoftware.sg.guice.DefaultGuiceInjectorFactory;
import com.silvermindsoftware.sg.guice.GuiceInjectorFactory;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletContext;

public class TestGuiceInjectorFactory extends DefaultGuiceInjectorFactory implements GuiceInjectorFactory {
    @NotNull
    public Injector getInjector(@NotNull ServletContext aServletContext) {
        final Injector injector = super.getInjector(aServletContext);
        InjectorHelper.init(injector);
        return injector;
    }
}
