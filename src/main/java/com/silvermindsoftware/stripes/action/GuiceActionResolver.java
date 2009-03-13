package com.silvermindsoftware.stripes.action;

import com.silvermindsoftware.stripes.integration.guice.GuiceContextListener;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.ActionResolver;
import net.sourceforge.stripes.controller.NameBasedActionResolver;

public class GuiceActionResolver extends NameBasedActionResolver implements ActionResolver {

    @Override
    public void init(Configuration configuration) throws Exception {
        super.init(configuration);
    }

    @Override
    protected ActionBean makeNewActionBean(Class<? extends ActionBean> type, ActionBeanContext context) throws Exception {
        return GuiceContextListener.getInjector().getInstance(type);
    }


}
