package com.silvermindsoftware.stripes.config;

import com.silvermindsoftware.stripes.integration.guice.GuiceContextListener;
import net.sourceforge.stripes.config.RuntimeConfiguration;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.exception.StripesRuntimeException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GuiceRuntimeConfiguration extends RuntimeConfiguration {

    @Override
    @SuppressWarnings("unchecked")
    protected Map<LifecycleStage, Collection<Interceptor>> initInterceptors(List classes) {

        Map<LifecycleStage, Collection<Interceptor>> map = new HashMap<LifecycleStage, Collection<Interceptor>>();

        for (Object type : classes) {
            try {
                Interceptor interceptor = (Interceptor) GuiceContextListener.getInjector().getInstance((Class) type);
                addInterceptor(map, interceptor);
            }
            catch (Exception e) {
                throw new StripesRuntimeException("Could not instantiate configured Interceptor ["
                        + type.getClass().getName() + "].", e);
            }
        }

        return map;
    }

//    @Override
//    @SuppressWarnings("unchecked")
//    protected <T extends ConfigurableComponent> T initializeComponent(Class<T> componentType,
//                                                                      String propertyName) {
//        Class clazz = getBootstrapPropertyResolver().getClassProperty(propertyName, componentType);
//        if (clazz != null) {
//            try {
//                T component = (T) GuiceContextListener.getInjector().getInstance(clazz);
//                component.init(this);
//                return component;
//            }
//            catch (Exception e) {
//                throw new StripesRuntimeException("Could not instantiate configured "
//                        + componentType.getSimpleName() + " of type [" + clazz.getSimpleName()
//                        + "]. Please check "
//                        + "the configuration parameters specified in your web.xml.", e);
//
//            }
//        } else {
//            return null;
//        }
//    }

}
