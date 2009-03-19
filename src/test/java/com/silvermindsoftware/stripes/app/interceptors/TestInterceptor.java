package com.silvermindsoftware.stripes.app.interceptors;

import com.google.inject.Inject;
import com.silvermindsoftware.stripes.app.service.TestInterceptorService;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

@Intercepts({
        LifecycleStage.ActionBeanResolution,
        LifecycleStage.HandlerResolution,
        LifecycleStage.BindingAndValidation,
        LifecycleStage.CustomValidation,
        LifecycleStage.EventHandling,
        LifecycleStage.ResolutionExecution,
        LifecycleStage.RequestInit,
        LifecycleStage.RequestComplete})
public class TestInterceptor implements Interceptor {

    protected TestInterceptorService testInterceptorService;

    @Inject
    public TestInterceptor(TestInterceptorService testInterceptorService) {
        this.testInterceptorService = testInterceptorService;
    }

    public Resolution intercept(ExecutionContext executionContext) throws Exception {
        testInterceptorService.getList();
        return executionContext.proceed();
    }
}
