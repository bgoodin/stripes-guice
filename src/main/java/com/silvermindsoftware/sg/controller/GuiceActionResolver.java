package com.silvermindsoftware.sg.controller;

import com.google.inject.Inject;
import com.google.inject.Injector;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.ActionResolver;
import net.sourceforge.stripes.controller.NameBasedActionResolver;
import org.jetbrains.annotations.NotNull;

public class GuiceActionResolver extends NameBasedActionResolver implements ActionResolver {
    @NotNull private final Injector theInjector;

    @Inject
    public GuiceActionResolver(@NotNull Injector anInjector) {
        theInjector = anInjector;
    }

    @NotNull
    @Override
    protected ActionBean makeNewActionBean(@NotNull Class<? extends ActionBean> aType,
                                           @NotNull ActionBeanContext aContext) {
        return theInjector.getInstance(aType);
    }
}
