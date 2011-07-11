package com.silvermindsoftware.sg.stripes.app.interceptors;

import com.google.inject.Inject;
import com.silvermindsoftware.sg.stripes.app.service.TestInterceptorService;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Intercepts({
    LifecycleStage.ActionBeanResolution,
    LifecycleStage.HandlerResolution,
    LifecycleStage.BindingAndValidation,
    LifecycleStage.CustomValidation,
    LifecycleStage.EventHandling,
    LifecycleStage.ResolutionExecution,
    LifecycleStage.RequestInit,
    LifecycleStage.RequestComplete
})
public class TestInterceptor implements Interceptor {
    @NotNull protected TestInterceptorService testInterceptorService;

    @Inject
    public TestInterceptor(@NotNull TestInterceptorService testInterceptorService) {
        this.testInterceptorService = testInterceptorService;
    }

    @Nullable
    public Resolution intercept(@NotNull ExecutionContext executionContext) throws Exception {
        testInterceptorService.getList();
        return executionContext.proceed();
    }
}
