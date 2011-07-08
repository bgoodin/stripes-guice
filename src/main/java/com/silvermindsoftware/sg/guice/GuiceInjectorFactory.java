package com.silvermindsoftware.sg.guice;

import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletContext;

public interface GuiceInjectorFactory {
    @NotNull
    Injector getInjector(@NotNull ServletContext aServletContext);
}
