package com.silvermindsoftware.stripes.controller;

import com.silvermindsoftware.stripes.integration.guice.GuiceContextListener;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.DefaultActionBeanContextFactory;
import net.sourceforge.stripes.exception.StripesServletException;
import net.sourceforge.stripes.util.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuiceActionBeanContextFactory extends DefaultActionBeanContextFactory {
    private static final Log log = Log.getInstance(DefaultActionBeanContextFactory.class);

    private Class<? extends ActionBeanContext> contextClass;

    @Override
    public void init(Configuration configuration) throws Exception {
        setConfiguration(configuration);

        Class<? extends ActionBeanContext> clazz = configuration.getBootstrapPropertyResolver()
                .getClassProperty(CONTEXT_CLASS_NAME, ActionBeanContext.class);
        if (clazz == null) {
            clazz = ActionBeanContext.class;
        } else {
            log.info(GuiceActionBeanContextFactory.class.getSimpleName(), " will use ",
                    ActionBeanContext.class.getSimpleName(), " subclass ", clazz.getName());
        }
        this.contextClass = clazz;
    }

    @Override
    public ActionBeanContext getContextInstance(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            ActionBeanContext context = GuiceContextListener.getInjector().getInstance(contextClass);
            context.setRequest(request);
            context.setResponse(response);
            return context;
        }
        catch (Exception e) {
            throw new StripesServletException("Could not instantiate configured " +
                    "ActionBeanContext class: " + this.contextClass, e);
        }
    }
}
