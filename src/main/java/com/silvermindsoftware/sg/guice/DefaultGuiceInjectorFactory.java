package com.silvermindsoftware.sg.guice;

import com.google.common.collect.Lists;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.util.List;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.Stage.DEVELOPMENT;
import static com.silvermindsoftware.sg.utils.Helpers.createClass;
import static com.silvermindsoftware.sg.utils.Helpers.getModuleNames;

public class DefaultGuiceInjectorFactory implements GuiceInjectorFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGuiceInjectorFactory.class);
    @NotNull private ServletContext theServletContext;

    @NotNull
    public Injector getInjector(@NotNull ServletContext aServletContext) {
        LOGGER.debug("Creating Injector");

        theServletContext = aServletContext;
        final List<Module> myModules = gatherModules();

        return createInjector(stage(), myModules);
    }

    @NotNull
    protected ServletContext getServletContext() {
        return theServletContext;
    }

    @NotNull
    protected Stage stage() {
        return DEVELOPMENT;
    }

    @NotNull
    protected List<Module> createModules(@NotNull String[] aModuleNames) {
        final List<Module> myModules = Lists.newArrayList();

        for (String myClassName : aModuleNames) {
            myModules.add(createClass(myClassName, Module.class));
        }

        return myModules;
    }

    @NotNull
    protected List<Module> additionalModules() {
        return Lists.newArrayList();
    }

    @NotNull
    private List<Module> gatherModules() {
        final List<Module> myModules = Lists.newArrayList();

        final String[] myModuleNames = getModuleNames(getServletContext());
        myModules.addAll(createModules(myModuleNames));
        myModules.addAll(additionalModules());

        return myModules;
    }
}
