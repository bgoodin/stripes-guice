package com.silvermindsoftware.stripes.controller;

import com.silvermindsoftware.stripes.integration.guice.GuiceContextListener;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.ActionResolver;
import net.sourceforge.stripes.controller.NameBasedActionResolver;

/**
 * <p>
 * Add the ActionResolver.Class init-param to your Stripes Filter and specify <code>com.silvermindsoftware.stripes.action.GuiceActionResolver</code>.
 * </p>
 * <p/>
 * <code>
 * &lt;filter&gt;<br/>
 * &nbsp;&nbsp;&lt;display-name&gt;Stripes Filter&lt;/display-name&gt;<br/>
 * &nbsp;&nbsp;&lt;filter-name&gt;StripesFilter&lt;/filter-name&gt;<br/>
 * &nbsp;&nbsp;&lt;filter-class&gt;net.sourceforge.stripes.controller.StripesFilter&lt;/filter-class&gt;<br/>
 * &nbsp;&nbsp;... other init-params<br/>
 * &nbsp;&nbsp;&lt;init-param&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;param-name&gt;ActionResolver.Class&lt;/param-name&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;param-value&gt;com.silvermindsoftware.stripes.action.GuiceActionResolver&lt;/param-value&gt;<br/>
 * &nbsp;&nbsp;&lt;/init-param&gt;<br/>
 * &lt;/filter&gt;<br/>
 * </code>
 */
public class GuiceActionResolver extends NameBasedActionResolver implements ActionResolver {

    @Override
    protected ActionBean makeNewActionBean(Class<? extends ActionBean> type, ActionBeanContext context) throws Exception {
        return GuiceContextListener.getInjector().getInstance(type);
    }


}
