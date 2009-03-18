package com.silvermindsoftware.stripes.app.extensions;

import net.sourceforge.stripes.action.ActionBeanContext;
import com.silvermindsoftware.stripes.app.service.TestService;
import com.silvermindsoftware.stripes.app.domain.TestUser;
import com.google.inject.Inject;

public class TestActionBeanContext extends ActionBeanContext {

    public static final Integer TEST_USER_ID = 123456;

    private TestService testService;

    public TestService getTestService() {
        return testService;
    }

    @Inject
    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    public TestUser getTestUser() {
        return testService.getUser(TEST_USER_ID);
    }

}
