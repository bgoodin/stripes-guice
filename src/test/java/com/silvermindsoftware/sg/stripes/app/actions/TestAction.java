package com.silvermindsoftware.sg.stripes.app.actions;

import com.google.inject.Inject;
import com.silvermindsoftware.sg.stripes.app.domain.TestUser;
import com.silvermindsoftware.sg.stripes.app.domain.TestWidget;
import com.silvermindsoftware.sg.stripes.app.extensions.TestActionBeanContext;
import com.silvermindsoftware.sg.stripes.app.extensions.TestTypeConverter;
import com.silvermindsoftware.sg.stripes.app.service.TestService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;

import java.util.List;

@UrlBinding("/testAction")
public class TestAction implements ActionBean {

    public static final String FETCH_LIST_RESOLUTION = "list.jsp";

    protected TestActionBeanContext actionBeanContext;
    protected TestUser testUser;
    protected TestWidget testWidget;
    @Validate(converter = TestTypeConverter.class)
    protected List testListType;

    protected TestService testService;

    @Inject
    public TestAction(TestService testService) {
        this.testService = testService;
    }

    @DefaultHandler
    public Resolution fetchList() {
        testUser = actionBeanContext.getTestUser();
        return new ForwardResolution(FETCH_LIST_RESOLUTION);
    }

    /* PROPERTIES */
    public void setContext(ActionBeanContext actionBeanContext) {
        this.actionBeanContext = (TestActionBeanContext) actionBeanContext;
    }

    public TestActionBeanContext getContext() {
        return actionBeanContext;
    }

    public TestUser getTestUser() {
        return testUser;
    }

    public void setTestUser(TestUser testUser) {
        this.testUser = testUser;
    }

    public TestWidget getTestWidget() {
        return testWidget;
    }

    @Inject
    public void setTestWidget(TestWidget testWidget) {
        this.testWidget = testWidget;
    }

    public List getTestListType() {
        return testListType;
    }

    public void setTestListType(List testListType) {
        this.testListType = testListType;
    }
}
