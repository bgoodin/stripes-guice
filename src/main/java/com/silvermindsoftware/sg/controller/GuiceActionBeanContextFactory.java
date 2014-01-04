package com.silvermindsoftware.sg.controller;

import com.google.inject.Inject;
import com.google.inject.Injector;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.DefaultActionBeanContextFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuiceActionBeanContextFactory extends DefaultActionBeanContextFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiceActionBeanContextFactory.class);
    @NotNull
    private final Injector theInjector;
    @NotNull
    private Class<? extends ActionBeanContext> theContextClass;

    @Inject
    public GuiceActionBeanContextFactory(@NotNull Injector anInjector) {
        theInjector = anInjector;
    }

    @Override
    public void init(@NotNull Configuration aConfiguration) throws Exception {
        setConfiguration(aConfiguration);

        final Class<? extends ActionBeanContext> myClass = getActionBeanClass(aConfiguration);
        LOGGER.info("Will use {} subclass {}", ActionBeanContext.class.getSimpleName(), myClass.getName());

        this.theContextClass = myClass;
    }

    @NotNull
    private Class<? extends ActionBeanContext> getActionBeanClass(@NotNull Configuration aConfiguration) {
        final Class<? extends ActionBeanContext> myClass =
                aConfiguration.getBootstrapPropertyResolver().getClassProperty(CONTEXT_CLASS_NAME, ActionBeanContext.class);

        return (myClass == null) ? ActionBeanContext.class : myClass;
    }

    @NotNull
    @Override
    public ActionBeanContext getContextInstance(@NotNull HttpServletRequest aRequest,
                                                @NotNull HttpServletResponse aResponse) {
        final ActionBeanContext myContext = theInjector.getInstance(theContextClass);
        myContext.setRequest(aRequest);
        myContext.setResponse(aResponse);
        return myContext;
    }
}
