package com.silvermindsoftware.sg.stripes.app.service;

import com.silvermindsoftware.sg.stripes.app.domain.TestUser;

import java.util.ArrayList;
import java.util.List;

public class TestServiceImpl implements TestService {

    public List<String> getList(Integer key) {
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        return list;
    }

    public TestUser getUser(Integer id) {
        return new TestUser(id, "First Name", "Last Name");
    }
}
