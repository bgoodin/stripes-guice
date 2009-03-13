package com.silvermindsoftware.stripes.integration.guice;

public class GuiceUtils {

    public static final String GUICE_MODULES_PARAM = "Guice.Modules";
    public static String GUICE_INJECTOR_FACTORY_CLASS_NAME = "GuiceInjectorFactory.Class";
    public static String DEFAULT_GUICE_INJECTOR_FACTORY_CLASS_NAME = "com.silvermindsoftware.stripes.integration.guice.DefaultGuiceInjectorFactory";

    public static String[] splitClasses(String moduleClasses) {
        return moduleClasses.split(",");
    }

}
