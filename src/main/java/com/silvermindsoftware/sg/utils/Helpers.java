package com.silvermindsoftware.sg.utils;

import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

import static com.silvermindsoftware.sg.exception.CreateClassException.createException;
import static com.silvermindsoftware.sg.utils.Constants.GUICE_MODULES_PARAM;

public enum Helpers {
    ;
    private static final Logger LOGGER = LoggerFactory.getLogger(Helpers.class);

    @NotNull
    public static <T> T createClass(@NotNull String aClassName, @NotNull Class<T> aTargetType) {
        try {
            return aTargetType.cast(Class.forName(aClassName.trim()).newInstance());
        } catch (Exception myException) {
            LOGGER.error("Exception when creating class", myException);
            throw createException(myException, aClassName);
        }
    }

    public static boolean isEmptyOrNull(@Nullable String aClassName) {
        return aClassName == null || aClassName.trim().isEmpty();
    }

    @NotNull
    public static String[] getModuleNames(@NotNull ServletContext aServletContext) {
        final String myGuiceModules = aServletContext.getInitParameter(GUICE_MODULES_PARAM);
        return splitClasses(myGuiceModules);
    }

    @NotNull
    public static String[] splitClasses(@Nullable String aModuleclasses) {
        return aModuleclasses != null ? aModuleclasses.split(",") : new String[0];
    }

    @NotNull
    public static Injector getInjector(@NotNull ServletContext aServletContext) {
        return Injector.class.cast(aServletContext.getAttribute(Injector.class.getName()));
    }
}
