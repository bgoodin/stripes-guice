package com.silvermindsoftware.sg.config;

import com.google.common.collect.Maps;
import com.google.inject.Injector;
import net.sourceforge.stripes.config.ConfigurableComponent;
import net.sourceforge.stripes.config.RuntimeConfiguration;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.exception.StripesRuntimeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.ServletContext;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.silvermindsoftware.sg.utils.Helpers.getInjector;


public class GuiceRuntimeConfiguration extends RuntimeConfiguration {
    @Nullable
    @Override
    protected <T extends ConfigurableComponent> T initializeComponent(@NotNull Class<T> aComponentType,
                                                                      @NotNull String aPropertyName) {
        final Class myClass = getBootstrapPropertyResolver().getClassProperty(aPropertyName, aComponentType);
        if (myClass != null) {
            try {
                @SuppressWarnings("unchecked")
                final Object myInstance = getInjectorFromContext().getInstance(myClass);
                final T myComponent = aComponentType.cast(myInstance);
                myComponent.init(this);
                return myComponent;
            } catch (Exception myException) {
                throw new StripesRuntimeException("Could not instantiate configured "
                        + aComponentType.getSimpleName() + " of type ["
                        + myClass.getSimpleName()
                        + "]. Please check "
                        + "the configuration parameters specified in your web.xml.",
                        myException);
            }
        } else {
            return null;
        }
    }

    @NotNull
    @Override
    protected Map<LifecycleStage, Collection<Interceptor>> initInterceptors(@NotNull List aClasses) {
        final Map<LifecycleStage, Collection<Interceptor>> myInterceptors = Maps.newHashMap();

        @SuppressWarnings("unchecked")
        final List<Class<Interceptor>> myInterceptorList = (List<Class<Interceptor>>) aClasses;
        for (Class<Interceptor> myType : myInterceptorList) {
            try {
                final Interceptor myInterceptor = getInjectorFromContext().getInstance(myType);
                addInterceptor(myInterceptors, myInterceptor);
            } catch (Exception myException) {
                throw new StripesRuntimeException("Could not instantiate configured Interceptor", myException);
            }
        }

        return myInterceptors;
    }

    @NotNull
    private Injector getInjectorFromContext() {
        final ServletContext myServletContext = getServletContext();
        return getInjector(myServletContext);
    }
}
