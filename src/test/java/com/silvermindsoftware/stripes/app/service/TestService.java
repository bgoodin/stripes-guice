package com.silvermindsoftware.stripes.app.service;

import com.google.inject.ImplementedBy;
import com.silvermindsoftware.stripes.app.domain.TestUser;

import java.util.List;

@ImplementedBy(TestServiceImpl.class)
public interface TestService {

    public List<String> getList(Integer key);

    public TestUser getUser(Integer id);

}
