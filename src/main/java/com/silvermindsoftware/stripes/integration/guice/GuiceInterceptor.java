package com.silvermindsoftware.stripes.integration.guice;

import com.google.inject.Injector;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

@Intercepts(LifecycleStage.ActionBeanResolution)
public class GuiceInterceptor implements Interceptor {

    public Resolution intercept(ExecutionContext context) throws Exception {

        Injector injector = GuiceContextListener.getInjector();
        injector.injectMembers(context.getActionBeanContext());
        Resolution resolution = context.proceed();
        return resolution;
    }
}
