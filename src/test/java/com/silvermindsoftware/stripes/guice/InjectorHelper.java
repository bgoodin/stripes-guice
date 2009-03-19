package com.silvermindsoftware.stripes.guice;

import com.google.inject.Injector;

public class InjectorHelper {

    private Injector injector;
    private static InjectorHelper helper;

    private InjectorHelper(Injector injector) {
        this.injector = injector;
    }

    private Injector getInjector() {
        return injector;
    }

    public static InjectorHelper init(Injector injector) {
        if (helper == null) {
            helper = new InjectorHelper(injector);
        }
        return helper;
    }

    public static Injector get() {
        if (helper == null) {
            throw new RuntimeException("You must call init first.");
        }
        return helper.getInjector();
    }

}
