package com.silvermindsoftware.stripes.app.service;

import java.util.ArrayList;
import java.util.List;

public class TestInterceptorServiceImpl implements TestInterceptorService {
    public List<String> getList() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        return list;
    }
}
