package com.silvermindsoftware.sg.stripes.app.service;

import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(TestInterceptorServiceImpl.class)
public interface TestInterceptorService {

    public List<String> getList();

}
