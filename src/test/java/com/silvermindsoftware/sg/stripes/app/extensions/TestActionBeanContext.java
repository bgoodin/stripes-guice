package com.silvermindsoftware.sg.stripes.app.extensions;

import com.google.inject.Inject;
import com.silvermindsoftware.sg.stripes.app.domain.TestUser;
import com.silvermindsoftware.sg.stripes.app.service.TestService;
import net.sourceforge.stripes.action.ActionBeanContext;

public class TestActionBeanContext extends ActionBeanContext {

    public static final Integer TEST_USER_ID = 123456;

    private TestService testService;

    @Inject
    public TestActionBeanContext(TestService testService) {
        this.testService = testService;
    }

    public TestService getTestService() {
        return testService;
    }

    public TestUser getTestUser() {
        return testService.getUser(TEST_USER_ID);
    }

}
